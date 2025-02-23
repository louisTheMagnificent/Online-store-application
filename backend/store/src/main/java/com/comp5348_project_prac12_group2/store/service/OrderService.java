package com.comp5348_project_prac12_group2.store.service;

import com.comp5348_project_prac12_group2.store.dto.OrderDTO;
import com.comp5348_project_prac12_group2.store.dto.OrderDetailDTO;
import com.comp5348_project_prac12_group2.store.dto.ProductDetailDTO;
import com.comp5348_project_prac12_group2.store.model.Customer;
import com.comp5348_project_prac12_group2.store.repository.UserRepository;
import com.comp5348_project_prac12_group2.store.util.LoggerUtil;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    GrpcOrderClient grpcOrderClient;

    @Autowired
    GrpcBankClient grpcBankClient;

    @Autowired
    GrpcWarehouseClient grpcWarehouseClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreService storeService;

    @Transactional
    public List<OrderDTO> getOrdersByUserId(Long userId) {
        return grpcOrderClient.getOrdersByCustomerId(userId);
    }


    @Transactional
    public OrderDetailDTO getOrderDetail(Long userId, Long orderId) {
        return grpcOrderClient.getOrderDetail(orderId);
    }

    @Transactional
    public boolean cancelOrder(Long userId, Long orderId) {
        OrderDetailDTO orderDetailDTO = getOrderDetail(userId,orderId);
        String sellerName = orderDetailDTO.getCustomerName();


        Customer seller = userRepository.findByUsername(sellerName);
        LoggerUtil.logInfo(sellerName+seller.getId());
        Optional<Customer> buyer = userRepository.findById(userId);
        if (buyer.isEmpty() || seller == null ){
            String emailMessage = "Order ID " + orderDetailDTO.getId() + " cannot be cancelled." +
                    " Reason: user not found.";
            rabbitTemplate.convertAndSend("order.exchange", "order.cancelled", emailMessage);
            return false; // User mismatch
        }
        boolean canRefund = grpcBankClient.checkSufficientFund(seller.getId(),orderDetailDTO.getTotalPrice());
        if (!canRefund){
            String emailMessage = "Order ID " + orderDetailDTO.getId() + " cannot be cancelled." +
                    " Reason: seller currently does not have enough balance for refund.";
            rabbitTemplate.convertAndSend("order.exchange", "order.cancelled", emailMessage);
            return false; // User mismatch
        }



        // Perform gRPC call to modify balances
        boolean refundSuccess = grpcBankClient.modifyBalance(
                buyer.get().getId(),
                seller.getId(),
                orderDetailDTO.getTotalPrice()
        );
        if (!refundSuccess){
            String emailMessage = "Order ID " + orderDetailDTO.getId() + " cannot be cancelled." +
                    " Reason: Either balance for seller was insufficient or account identification failed.";
            rabbitTemplate.convertAndSend("order.exchange", "order.cancelled", emailMessage);

            return false; // Order not found or cannot be cancelled
        }


        boolean hasCancelled =  grpcOrderClient.cancelOrder(orderDetailDTO.getId());
        if (hasCancelled){
            // Send message to email service through RabbitMQ
            String emailMessage = "Order ID " + orderDetailDTO.getId() + " has been cancelled." +
                    " Returned balance: "+orderDetailDTO.getTotalPrice();
            rabbitTemplate.convertAndSend("order.exchange", "order.cancelled", emailMessage);

            return true;
        }

        String emailMessage = "Order ID " + orderDetailDTO.getId() + " cannot be cancelled." +
                " Reason: Either balance for seller was insufficient or account identification failed.";
        rabbitTemplate.convertAndSend("order.exchange", "order.cancelled", emailMessage);

        return false; // Order not found or cannot be cancelled
    }

    @Transactional
    public boolean MakeAnOrder(Long userId, Long productId, String sellerName, Integer amount){
        Customer seller = userRepository.findByUsername(sellerName);
        Customer buyer = userRepository.getReferenceById(userId);
        if (seller == null){
            return false; //user not exist
        }

        ProductDetailDTO product = storeService.getProductStockByProductIdAndCustomerId(productId,seller.getId());

        Integer quantityInStock = product.getQuantity();
        boolean canPurchase = grpcBankClient.checkSufficientFund(buyer.getId(),amount * product.getPrice());
        if (quantityInStock < amount || !canPurchase){
            return false;
        }
        // Perform payment logic here (not implemented)
        boolean paymentSuccess = grpcBankClient.modifyBalance(
                seller.getId(),//seller
                userId, //buyer
                amount*product.getPrice()
        );
        if (!paymentSuccess){
            return false;
        }

        boolean canCreateOrder = grpcOrderClient.createOrder(userId,seller.getId(),product.getName(),amount,amount*product.getPrice(),productId);
        if (!canCreateOrder){
            return false;
        }
        return true;

    }





}
