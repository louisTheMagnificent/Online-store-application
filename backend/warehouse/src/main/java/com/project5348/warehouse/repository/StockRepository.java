package com.project5348.warehouse.repository;

import com.project5348.warehouse.dao.DetailProductDao;
import com.project5348.warehouse.dao.StockDao;
import com.project5348.warehouse.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    // Method to get stocks categorized by customerId and product name
    @Query("SELECT new com.project5348.warehouse.dao.StockDao(p.productName, SUM(s.quantity), st.customer, p.id) " +
            "FROM Stock s " +
            "JOIN s.product p " +
            "JOIN s.storage st " +
            "GROUP BY p.productName, st.customer,p.id")
    List<StockDao> findAggregatedStockByCustomerAndProduct();


    @Query("SELECT new com.project5348.warehouse.dao.DetailProductDao(p.id, p.productName, p.description, p.price, SUM(s.quantity)) " +
            "FROM Stock s " +
            "JOIN s.product p " +
            "JOIN s.storage st " +
            "WHERE p.id = :productId AND st.customer = :customerId " +
            "GROUP BY p.id, p.productName, p.description, p.price")
    DetailProductDao findProductStockByProductIdAndCustomerId(@Param("productId") Long productId, @Param("customerId") Long customerId);


    @Query("SELECT s FROM Stock s WHERE s.storage.customer = :sellerId AND s.product.id = :productId ORDER BY s.quantity DESC")
    List<Stock> findStocksBySellerIdAndProductId(@Param("sellerId") Long sellerId, @Param("productId") Long productId);

}
