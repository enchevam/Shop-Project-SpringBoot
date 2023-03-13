package com.project.ProjectShop.ProjectShop.repositories;

import com.project.ProjectShop.ProjectShop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query(value = "SELECT * FROM products WHERE name LIKE %?1% OR color LIKE %?1% OR product_type LIKE %?1% AND quantity > 0 " , nativeQuery = true)
    public List<Product> search(String keyword);
}
