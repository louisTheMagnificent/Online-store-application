package com.project5348.warehouse.service;

import com.project5348.warehouse.dao.DetailProductDao;
import com.project5348.warehouse.dao.StockDao;
import com.project5348.warehouse.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    @Autowired
    private StockRepository stockRepository;

    // Method to return aggregated stock data grouped by customer and product
    public List<StockDao> getAggregatedStockByCustomerAndProduct() {
        return stockRepository.findAggregatedStockByCustomerAndProduct();
    }

    // Method to return detailed product information along with aggregated stock quantity for a specific product and customer
    public DetailProductDao getProductStockByProductIdAndCustomerId(Long productId, Long customerId) {
        return stockRepository.findProductStockByProductIdAndCustomerId(productId, customerId);
    }


}
