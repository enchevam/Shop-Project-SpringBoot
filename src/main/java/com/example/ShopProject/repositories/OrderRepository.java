package com.example.ShopProject.repositories;

import com.example.ShopProject.entities.Customer;
import com.example.ShopProject.entities.Order;
import com.example.ShopProject.utils.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("SELECT o FROM Order o WHERE (:orderStatus IS NULL OR o.orderStatus = :orderStatus)")
    List<Order> getOrdersByStatus(@Param("orderStatus") OrderStatus orderStatus);


    List<Order> findByCustomerOrderByOrderDateDesc(Customer customer);

    List<Order> findByCustomer(Customer customer);
}
