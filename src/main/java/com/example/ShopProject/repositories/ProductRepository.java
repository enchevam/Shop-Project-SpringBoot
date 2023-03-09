package com.example.ShopProject.repositories;

import com.example.ShopProject.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {


    //List<Product> findByNameContainingIgnoreCase(String searchQuery);

    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    List<Product> findByExpireInAfter(LocalDate today);

    List<Product> findByQuantity(int quantity);
}
