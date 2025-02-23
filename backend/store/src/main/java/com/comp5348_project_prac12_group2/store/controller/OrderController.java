package com.comp5348_project_prac12_group2.store.controller;

import com.comp5348_project_prac12_group2.store.dto.OrderDTO;
import com.comp5348_project_prac12_group2.store.dto.OrderDetailDTO;
import com.comp5348_project_prac12_group2.store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8085")
@RestController
@RequestMapping("/buyer/{user_id}")
public class OrderController {

    @Autowired
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable("user_id") Long userId) {
        List<OrderDTO> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/{order_id}")
    public ResponseEntity<OrderDetailDTO> getOrderDetail(@PathVariable("user_id") Long userId, @PathVariable("order_id") Long orderId) {
        OrderDetailDTO orderDetail = orderService.getOrderDetail(userId, orderId);
        return ResponseEntity.ok(orderDetail);
    }

    @PostMapping("/orders/{order_id}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long user_id, @PathVariable Long order_id) {
        boolean success = orderService.cancelOrder(user_id, order_id);
        if (success) {
            return ResponseEntity.ok("Order cancelled successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order cannot be cancelled at this stage");
        }
    }
    @PostMapping("/products/{productId}/{sellerName}")
    public ResponseEntity<?> makeAnOrder(@PathVariable("user_id") Long userId,
                                         @PathVariable("productId") Long productId,
                                         @PathVariable("sellerName") String sellerName,
                                         @RequestParam("amount") Integer amount){
        boolean success = orderService.MakeAnOrder(userId,productId,sellerName,amount);
        if (success) {
            return ResponseEntity.ok("Order made successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order cannot be made at this stage");
        }
    }


}


