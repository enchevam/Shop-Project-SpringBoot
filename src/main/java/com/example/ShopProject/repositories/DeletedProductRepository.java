package com.example.ShopProject.repositories;

import com.example.ShopProject.entities.DeletedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletedProductRepository extends JpaRepository<DeletedProduct, Long> {
}
