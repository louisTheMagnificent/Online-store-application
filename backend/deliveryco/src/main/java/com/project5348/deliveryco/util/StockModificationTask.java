package com.project5348.deliveryco.util;
import com.project5348.deliveryco.service.StockModificationClient;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Callable;

public class StockModificationTask implements Callable<Boolean> {
    private final Long sellerId;
    private final Long productId;
    private final Integer itemQuantities;

    private final StockModificationClient stockModificationClient;

    public StockModificationTask(Long sellerId, Long productId, Integer itemQuantities, StockModificationClient stockModificationClient) {
        this.sellerId = sellerId;
        this.productId = productId;
        this.itemQuantities = itemQuantities;
        this.stockModificationClient = stockModificationClient;
    }

    @Override
    public Boolean call() {
        // Pause for a specified duration
        try {
            Thread.sleep(10000); // 10000 milliseconds = 10 seconds
        } catch (InterruptedException e) {
            // Handle the interruption
            Thread.currentThread().interrupt(); // Restore the interrupted status
            // Log the error or handle it appropriately
            System.err.println("Sleep interrupted: " + e.getMessage());
            return false; // Return false if interrupted
        }

        return stockModificationClient.modifyStock(sellerId, productId, itemQuantities);
    }
}
