package com.example.ShopProject.repositories;

import com.example.ShopProject.entities.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {
}
