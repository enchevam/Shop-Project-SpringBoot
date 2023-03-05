package com.project.ProjectShop.ProjectShop.repositories;

import com.project.ProjectShop.ProjectShop.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
