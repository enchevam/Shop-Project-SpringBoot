package com.example.ShopProject.services;

import com.example.ShopProject.entities.Order;
import com.example.ShopProject.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CheckoutService {

    @Autowired
    private OrderRepository orderRepository;

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }


}
