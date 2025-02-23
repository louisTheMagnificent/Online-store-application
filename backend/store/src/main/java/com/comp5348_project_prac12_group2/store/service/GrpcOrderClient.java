package com.comp5348_project_prac12_group2.store.service;


import com.comp5348_project_prac12_group2.store.dto.OrderDTO;
import com.comp5348_project_prac12_group2.store.dto.OrderDetailDTO;
import com.comp5348_project_prac12_group2.store.repository.UserRepository;
import deliveryco.Deliveryco;
import deliveryco.OrderServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrpcOrderClient {
    private final OrderServiceGrpc.OrderServiceBlockingStub blockingStub;

    @Autowired
    private UserRepository userRepository;



    public GrpcOrderClient(UserRepository userRepository) {
        // Replace "localhost" and "port" with your gRPC server's address and port
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9093)
                .usePlaintext() // Disable SSL/TLS for testing
                .build();
        blockingStub = OrderServiceGrpc.newBlockingStub(channel);
        this.userRepository = userRepository;
    }

    public List<OrderDTO> getOrdersByCustomerId(Long customerId) {
        Deliveryco.OrderRequest request = Deliveryco.OrderRequest.newBuilder()
                .setCustomerId(customerId)
                .build();

        Deliveryco.OrderResponse response;
        try {
            response = blockingStub.findAllOrdersByCustomerId(request);
        } catch (StatusRuntimeException e) {
            System.err.println("RPC failed: " + e.getStatus());
            return List.of(); // Return an empty list on error
        }

        // Map the response to a list of OrderDTOs
        return response.getOrdersList().stream()
                .map(orderDao -> new OrderDTO(
                        orderDao.getProductName(),
                        orderDao.getItemQuantities(),
                        userRepository.findById(orderDao.getCustomerId()).get().getUsername(),
                        orderDao.getId()
                ))
                .collect(Collectors.toList());
    }

    public OrderDetailDTO getOrderDetail(Long orderId) {
        Deliveryco.OrderDetailRequest request = Deliveryco.OrderDetailRequest.newBuilder()
                .setId(orderId)
                .build();

        Deliveryco.OrderDetailResponse response;
        try {
            response = blockingStub.findOrderDetailById(request);
        } catch (StatusRuntimeException e) {
            System.err.println("RPC failed: " + e.getStatus());
            return null; // Return null or throw an exception based on your error handling strategy
        }

        // Map the response to an OrderDetailDTO
        return new OrderDetailDTO(
                response.getId(),
                userRepository.findById(response.getCustomerId()).get().getUsername(),
                response.getProductName(),
                response.getItemQuantities(),
                response.getTotalPrice(),
                response.getTime(),
                response.getStatus(),
                response.getShowCancelBtn()
        );
    }


    public boolean cancelOrder(Long orderId) {
        // Create the request
        Deliveryco.CancelOrderRequest request = Deliveryco.CancelOrderRequest.newBuilder()
                .setOrderId(orderId)
                .build();

        try {
            // Call the cancelOrder method
            Deliveryco.CancelOrderResponse response = blockingStub.cancelOrder(request);
            return response.getSuccess(); // Return the success status
        } catch (StatusRuntimeException e) {
            System.err.println("RPC failed: " + e.getStatus());
            return false; // Handle error appropriately
        }
    }

    public boolean createOrder(Long customerId, Long sellerId, String productName, Integer itemQuantities, Double totalPrice, Long productId) {
        // Build the request
        Deliveryco.CreateOrderRequest request = Deliveryco.CreateOrderRequest.newBuilder()
                .setCustomerId(customerId)
                .setSellerId(sellerId)
                .setProductName(productName)
                .setProductId(productId)
                .setItemQuantities(itemQuantities)
                .setTotalPrice(totalPrice)
                .build();

        // Call the CreateOrder RPC method
        Deliveryco.CreateOrderResponse response;
        try {
            response = blockingStub.createOrder(request);
            // Handle the response
            if (response.getSuccess()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
