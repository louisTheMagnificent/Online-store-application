package com.comp5348_project_prac12_group2.store.service;


import com.comp5348_project_prac12_group2.store.dto.OrderDetailDTO;
import com.comp5348_project_prac12_group2.store.dto.ProductDetailDTO;
import com.comp5348_project_prac12_group2.store.dto.StockDTO;

import com.comp5348_project_prac12_group2.store.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import warehouse.WarehouseServiceGrpc;
import warehouse.Warehouse;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreService {

    @Autowired
    GrpcWarehouseClient grpcWarehouseClient;

    @Autowired
    private UserRepository userRepository;

    public StoreService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public List<StockDTO> getAggregatedStockByCustomerAndProduct() {
        List<StockDTO> stockDTOs = new ArrayList<>();
        for (Warehouse.AggregatedStock stock : grpcWarehouseClient.getAggregatedStockByCustomerAndProduct()) {
            StockDTO stockDTO = new StockDTO(
                    stock.getProductName(),
                    userRepository.getReferenceById(stock.getCustomerId()).getUsername(),
                    stock.getProductId(),
                    stock.getQuantity()
            );
            stockDTOs.add(stockDTO);
        }
        return stockDTOs;
    }

    @Transactional
    public ProductDetailDTO getProductStockByProductIdAndCustomerId(Long productId, Long customerId) {

        Warehouse.ProductStockDetails response = grpcWarehouseClient.getProductStockByProductIdAndCustomerId(productId,customerId);
        return new ProductDetailDTO(
                response.getProductId(),
                response.getProductName(),
                response.getDescription(),
                response.getPrice(),
                response.getQuantity()
        );
    }




}


