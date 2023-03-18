package com.example.ShopProject.repositories;

import com.example.ShopProject.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE (:id is null or p.id = :id) " +
            "AND (:name is null or lower(p.name) like lower(concat('%', :name, '%'))) " +
            "AND (:quantity is null or p.quantity = :quantity) " +
            "AND (:minPrice is null or p.price >= :minPrice) " +
            "AND (:maxPrice is null or p.price <= :maxPrice)")
    List<Product> searchProducts(@Param("id") Long id, @Param("name") String name, @Param("quantity") Integer quantity,
                                 @Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);

    @Query(value = "SELECT * FROM products WHERE name LIKE %?1% OR color LIKE %?1% OR product_type LIKE %?1% AND quantity > 0 " , nativeQuery = true)
    public List<Product> search(String keyword);
}