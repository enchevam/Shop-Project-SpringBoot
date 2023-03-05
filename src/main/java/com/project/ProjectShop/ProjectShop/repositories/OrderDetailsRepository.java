package com.project.ProjectShop.ProjectShop.repositories;


import com.project.ProjectShop.ProjectShop.entities.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderItems,Long> {
}
