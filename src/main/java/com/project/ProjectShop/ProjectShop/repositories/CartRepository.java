package com.project.ProjectShop.ProjectShop.repositories;

import com.project.ProjectShop.ProjectShop.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
