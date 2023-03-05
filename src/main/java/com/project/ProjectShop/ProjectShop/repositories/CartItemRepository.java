package com.project.ProjectShop.ProjectShop.repositories;

import com.project.ProjectShop.ProjectShop.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
