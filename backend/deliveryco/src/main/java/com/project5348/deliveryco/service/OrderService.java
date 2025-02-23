package com.project5348.deliveryco.service;

import com.project5348.deliveryco.dao.DetailOrderDao;
import com.project5348.deliveryco.dao.OrderDao;
import com.project5348.deliveryco.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import com.project5348.deliveryco.model.Order;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private ScheduledFuture<?> scheduledFuture; // Store the future to cancel it later

    // Create the ScheduledExecutorService instance
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public List<OrderDao> findAllOrdersByCustomerId(Long customerId){
        List<Order> orders = orderRepository.findAllByCustomerId(customerId);
        List<OrderDao> orderDaos = new ArrayList<>();
        if (orders != null){
            for (Order order: orders){
                orderDaos.add(new OrderDao(order.getProductName(),order.getItemQuantities(),order.getCustomerId(),order.getId()));
            }
        }
        return orderDaos;
    }

    @Transactional
    public DetailOrderDao findOrderDetailById(Long id){
        Order order = orderRepository.getReferenceById(id);
        boolean showCancelBtn = order.getStatus() == Order.OrderStatus.PAID || order.getStatus() == Order.OrderStatus.READY_FOR_PICKUP;

        return new DetailOrderDao(order.getId(),order.getSellerId(),order.getProductName(),
                order.getItemQuantities(),order.getTotalPrice(),order.getTime(),order.getStatus().name(),showCancelBtn);
    }

    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.getReferenceById(orderId);
        order.setStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(order);
    }


    public Long createOrder(Long customerId, Long sellerId, String productName, Integer itemQuantities,Double totalPrice){
        Order order =  new Order(customerId,sellerId,productName,itemQuantities,totalPrice, LocalDateTime.now(), Order.OrderStatus.READY_FOR_PICKUP);
        orderRepository.save(order);
        return order.getId();
    }

    public boolean changeOrderState(Long orderId) {
        // Fetch the order eagerly to avoid LazyInitializationException
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + orderId));

        if (order.getStatus() == Order.OrderStatus.READY_FOR_PICKUP) {
            order.setStatus(Order.OrderStatus.IN_DEPOT);
        } else if (order.getStatus() == Order.OrderStatus.IN_DEPOT) {
            order.setStatus(Order.OrderStatus.ON_DELIVERY_TRUCK);
        } else if (order.getStatus() == Order.OrderStatus.ON_DELIVERY_TRUCK) {
            order.setStatus(Order.OrderStatus.DELIVERED);
        }

        orderRepository.save(order);

        // Send message to the queue
        String message = "Order " + orderId + " status changed to " + order.getStatus();
        rabbitTemplate.convertAndSend("order.queue", message);

        // Return true if the order status is DELIVERED
        return order.getStatus() == Order.OrderStatus.DELIVERED;
    }

    public void scheduleOrderStateChange(Long orderId) {
        scheduledFuture = scheduler.scheduleAtFixedRate(() -> {
            boolean isDelivered = changeOrderState(orderId);
            if (isDelivered) {
                scheduledFuture.cancel(false); // Cancel the scheduled task
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    public synchronized boolean cancelOrderIfReady(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + orderId));

        if (order.getStatus() == Order.OrderStatus.READY_FOR_PICKUP) {
            order.setStatus(Order.OrderStatus.CANCELLED);
            orderRepository.save(order);
            return true;
        }
        return false;
    }
}
