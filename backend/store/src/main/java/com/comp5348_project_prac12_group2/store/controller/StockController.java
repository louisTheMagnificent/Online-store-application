package com.comp5348_project_prac12_group2.store.controller;

import com.comp5348_project_prac12_group2.store.dto.StockDTO;
import com.comp5348_project_prac12_group2.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8085")
@RestController
public class StockController {
    @Autowired
    private StoreService storeService;

    @GetMapping("/buyer/{user_id}/products")
    public ResponseEntity<List<StockDTO>> listAllStock(@PathVariable("user_id") Long userId) {

        List<StockDTO> stocks = storeService.getAggregatedStockByCustomerAndProduct();
        return ResponseEntity.ok(stocks);
    }


}


