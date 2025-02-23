package com.project5348.warehouse.rpc;
import io.grpc.stub.StreamObserver;
import jakarta.transaction.Transactional;
import net.devh.boot.grpc.server.service.GrpcService;
import warehouse.Modifystock;
import warehouse.StockModificationServiceGrpc;
import org.springframework.beans.factory.annotation.Autowired;
import com.project5348.warehouse.model.Stock;
import com.project5348.warehouse.repository.StockRepository;

import java.util.List;

@GrpcService
public class StockModificationServiceImpl extends StockModificationServiceGrpc.StockModificationServiceImplBase {

    @Autowired
    private final StockRepository stockRepository;

    @Autowired
    public StockModificationServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void modifyStock(Modifystock.ModifyStockRequest request, StreamObserver<Modifystock.ModifyStockResponse> responseObserver) {
        Long sellerId = request.getSellerId();
        Long productId = request.getProductId();
        int quantityToDeduct = request.getQuantityToDeduct();

        // Retrieve all stocks for the given seller and product
        List<Stock> stocks = stockRepository.findStocksBySellerIdAndProductId(sellerId, productId);

        int remainingQuantity = quantityToDeduct;
        boolean stockModified = false;

        for (Stock stock : stocks) {
            int currentStockQuantity = stock.getQuantity();

            if (currentStockQuantity >= remainingQuantity) {
                // Current stock can fulfill the entire remaining quantity
                stock.setQuantity(currentStockQuantity - remainingQuantity);
                stockRepository.save(stock);  // Update stock
                stockModified = true;
                break; // Exit the loop as the order is fulfilled
            } else {
                // Deduct whatever stock we can from this storage
                remainingQuantity -= currentStockQuantity;
                stock.setQuantity(0); // All stock in this storage is allocated
                stockRepository.save(stock); // Update stock
            }
        }

        // Prepare the response
        Modifystock.ModifyStockResponse response = Modifystock.ModifyStockResponse.newBuilder()
                .setSuccess(stockModified)
                .build();

        // Send the response back to the client
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
