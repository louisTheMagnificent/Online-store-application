package com.comp5348_project_prac12_group2.store.controller;

import com.comp5348_project_prac12_group2.store.dto.ProductDetailDTO;
import com.comp5348_project_prac12_group2.store.model.Customer;
import com.comp5348_project_prac12_group2.store.service.StoreService;
import com.comp5348_project_prac12_group2.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8085")
@RestController
@RequestMapping("/buyer/{userId}/products")
public class ProductController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private UserService userService;

    @GetMapping("/{productId}/{sellerName}")
    public ResponseEntity<ProductDetailDTO> getOneProduct(@PathVariable("userId") Long userId,
                                                          @PathVariable("productId") Long productId,
                                                          @PathVariable("sellerName") String sellerName) {
        Customer seller = userService.getSellerExistsByUsername(sellerName);
        if (seller == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        try {
            ProductDetailDTO productDetail = storeService.getProductStockByProductIdAndCustomerId(productId,seller.getId());
            return ResponseEntity.ok(productDetail); // Return 200 OK with product details
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 Not Found if product doesn't exist
        }
    }
}




