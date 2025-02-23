package com.project5348.deliveryco.rpc;


import com.project5348.deliveryco.dao.DetailOrderDao;
import com.project5348.deliveryco.dao.OrderDao;
import com.project5348.deliveryco.model.Order;
import com.project5348.deliveryco.service.StockModificationClient;
import com.project5348.deliveryco.util.StockModificationTask;
import deliveryco.Deliveryco;
import deliveryco.OrderServiceGrpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project5348.deliveryco.service.OrderService;

import java.util.List;
import java.util.concurrent.*;

@GrpcService
public class OrderServiceImpl extends OrderServiceGrpc.OrderServiceImplBase {

    @Autowired
    private OrderService orderService;

    @Autowired
    private StockModificationClient stockModificationClient;

    @Override
    public void findAllOrdersByCustomerId(Deliveryco.OrderRequest request, StreamObserver<Deliveryco.OrderResponse> responseObserver) {
        Long customerId = request.getCustomerId();
        List<OrderDao> orders = orderService.findAllOrdersByCustomerId(customerId);
        Deliveryco.OrderResponse.Builder responseBuilder = Deliveryco.OrderResponse.newBuilder();

        for (OrderDao order : orders) {
            // Create a new OrderDao message for each order
            Deliveryco.OrderDao orderDao = Deliveryco.OrderDao.newBuilder()
                    .setProductName(order.getProductName())
                    .setItemQuantities(order.getItemQuantity())
                    .setCustomerId(order.getUserId())
                    .setId(order.getId())
                    .build();
            responseBuilder.addOrders(orderDao);
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void findOrderDetailById(Deliveryco.OrderDetailRequest request, StreamObserver<Deliveryco.OrderDetailResponse> responseObserver) {
        Long id = request.getId();
        DetailOrderDao orderDetail = orderService.findOrderDetailById(id);

        // Build the OrderDetailResponse from the orderDetail
        Deliveryco.OrderDetailResponse response = Deliveryco.OrderDetailResponse.newBuilder()
                .setId(orderDetail.getId())
                .setCustomerId(orderDetail.getCustomerId())
                .setProductName(orderDetail.getProductName())
                .setItemQuantities(orderDetail.getItemQuantities())
                .setTotalPrice(orderDetail.getTotalPrice())
                .setTime(orderDetail.getTime().toString()) // Ensure you handle this appropriately
                .setStatus(orderDetail.getStatus())
                .setShowCancelBtn(orderDetail.isShowCancelButton())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    Future<Boolean> stockFuture; // Store the future for stock modification
    @Override
    public void createOrder(Deliveryco.CreateOrderRequest request, StreamObserver<Deliveryco.CreateOrderResponse> responseObserver) {
        Long customerId = request.getCustomerId();
        Long sellerId = request.getSellerId();
        String productName = request.getProductName();
        Integer itemQuantities = request.getItemQuantities();
        Double totalPrice = request.getTotalPrice();
        Long productId = request.getProductId();

        Long orderId = orderService.createOrder(customerId, sellerId, productName, itemQuantities, totalPrice);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        StockModificationTask stockTask = new StockModificationTask(sellerId, productId, itemQuantities, stockModificationClient);

        // Start stock modification in a new thread without blocking the response
        stockFuture = executor.submit(stockTask);

        // Send immediate response back to the client
        Deliveryco.CreateOrderResponse response = Deliveryco.CreateOrderResponse.newBuilder()
                .setSuccess(true)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();

        // Optionally, handle stock modification completion in a separate method
        executor.execute(() -> {
            try {
                // Wait for stock modification to complete
                boolean stockModified = stockFuture.get(); // This will block until completion

                // Schedule state changes for the new order if stock modification was successful
                if (stockModified) {
                    orderService.scheduleOrderStateChange(orderId);
                } else {
                    System.err.println("Stock modification failed for orderId: " + orderId);
                    // Optionally handle order rollback
                }
            } catch (CancellationException e) {
                System.err.println("Stock modification was cancelled: " + e.getMessage());
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Error during stock modification: " + e.getMessage());
            } finally {
                executor.shutdown(); // Ensure the executor is shut down
            }
        });
    }

    @Override
    public void cancelOrder(Deliveryco.CancelOrderRequest request, StreamObserver<Deliveryco.CancelOrderResponse> responseObserver) {
        Long orderId = request.getOrderId();

        // Cancel the stock modification task if it's still running
        if (stockFuture != null) {
            stockFuture.cancel(true); // Attempt to cancel the stock modification
        }

        // Continue with your cancellation logic
        try {
            orderService.cancelOrder(orderId);
            Deliveryco.CancelOrderResponse response = Deliveryco.CancelOrderResponse.newBuilder()
                    .setSuccess(true)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            // Handle the cancellation error
            Deliveryco.CancelOrderResponse response = Deliveryco.CancelOrderResponse.newBuilder()
                    .setSuccess(false)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            System.err.println("Error during canceling order: " + e.getMessage());
        }
    }





}


