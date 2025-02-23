package com.project5348.deliveryco.service;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;
import warehouse.Modifystock;
import warehouse.StockModificationServiceGrpc;

@Service
public class StockModificationClient {

    private final StockModificationServiceGrpc.StockModificationServiceBlockingStub stockModificationService;

    public StockModificationClient() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9092)
                .usePlaintext()
                .build();
        stockModificationService = StockModificationServiceGrpc.newBlockingStub(channel);
    }

    public boolean modifyStock(Long sellerId, Long productId, Integer quantityToDeduct) {
        Modifystock.ModifyStockRequest request = Modifystock.ModifyStockRequest.newBuilder()
                .setSellerId(sellerId)
                .setProductId(productId)
                .setQuantityToDeduct(quantityToDeduct)
                .build();

        Modifystock.ModifyStockResponse response = stockModificationService.modifyStock(request);
        return response.getSuccess();
    }
}
