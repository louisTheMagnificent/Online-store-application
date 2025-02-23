package com.comp5348_project_prac12_group2.store.service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Service;
import warehouse.WarehouseServiceGrpc;
import warehouse.Warehouse;

import java.util.List;

@Service
public class GrpcWarehouseClient {

    private final WarehouseServiceGrpc.WarehouseServiceBlockingStub warehouseStub;

    public GrpcWarehouseClient()  {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9092)
                .usePlaintext()
                .build();
        this.warehouseStub = WarehouseServiceGrpc.newBlockingStub(channel);
    }

    public List<Warehouse.AggregatedStock> getAggregatedStockByCustomerAndProduct() {
        Warehouse.EmptyRequest request = Warehouse.EmptyRequest.newBuilder().build();
        Warehouse.AggregatedStockResponse response;

        try {
            response = warehouseStub.getAggregatedStockByCustomerAndProduct(request);
            return response.getAggregatedStocksList();
        } catch (StatusRuntimeException e) {
            System.err.println("RPC failed: " + e.getStatus());
            return List.of(); // Return an empty list on failure
        }
    }


    public Warehouse.ProductStockDetails getProductStockByProductIdAndCustomerId(Long productId, Long customerId) {
        Warehouse.ProductCustomerRequest request = Warehouse.ProductCustomerRequest.newBuilder()
                .setProductId(productId)
                .setCustomerId(customerId)
                .build();

        try {
            return warehouseStub.getProductStockByProductIdAndCustomerId(request);
        } catch (StatusRuntimeException e) {
            System.err.println("RPC failed: " + e.getStatus());
            return null; // Handle appropriately
        }
    }




}


