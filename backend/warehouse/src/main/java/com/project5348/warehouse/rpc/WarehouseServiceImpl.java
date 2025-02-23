package com.project5348.warehouse.rpc;

import com.project5348.warehouse.dao.DetailProductDao;
import com.project5348.warehouse.dao.StockDao;
import com.project5348.warehouse.service.WarehouseService;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import warehouse.Warehouse;
import warehouse.WarehouseServiceGrpc;

import java.util.List;
import java.util.Optional;

@GrpcService
public class WarehouseServiceImpl extends WarehouseServiceGrpc.WarehouseServiceImplBase {

    @Autowired
    private WarehouseService warehouseService;

    @Override
    public void getAggregatedStockByCustomerAndProduct(Warehouse.EmptyRequest request, StreamObserver<Warehouse.AggregatedStockResponse> responseObserver) {
        List<StockDao> stocks = warehouseService.getAggregatedStockByCustomerAndProduct();

        Warehouse.AggregatedStockResponse.Builder responseBuilder = Warehouse.AggregatedStockResponse.newBuilder();

        for (StockDao stock : stocks) {
            Warehouse.AggregatedStock aggregatedStock = Warehouse.AggregatedStock.newBuilder()
                    .setProductName(stock.getProductName())
                    .setCustomerId(stock.getCustomerId())
                    .setProductId(stock.getProductId())
                    .setQuantity(stock.getAggregatedQuantity())
                    .build();

            responseBuilder.addAggregatedStocks(aggregatedStock);
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getProductStockByProductIdAndCustomerId(Warehouse.ProductCustomerRequest request, StreamObserver<Warehouse.ProductStockDetails> responseObserver) {
        // Get the product stock details for the given product ID and customer ID
        DetailProductDao productDetail = warehouseService.getProductStockByProductIdAndCustomerId(request.getProductId(), request.getCustomerId());

        // Check if the product stock details are found
        if (productDetail != null) {
            // Build the response with the product stock details
            Warehouse.ProductStockDetails productStockDetail = Warehouse.ProductStockDetails.newBuilder()
                    .setProductId(productDetail.getId())
                    .setProductName(productDetail.getName())
                    .setDescription(productDetail.getDescription())
                    .setPrice(productDetail.getPrice())
                    .setQuantity(productDetail.getQuantity())
                    .build();

            responseObserver.onNext(productStockDetail);
        } else {
            // Handle the case where no product stock is found
            responseObserver.onError(new StatusRuntimeException(Status.NOT_FOUND.withDescription("Product stock not found.")));
        }

        // Complete the response
        responseObserver.onCompleted();
    }


}
