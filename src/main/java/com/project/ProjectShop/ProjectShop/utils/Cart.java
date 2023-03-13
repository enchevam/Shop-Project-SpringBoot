package com.project.ProjectShop.ProjectShop.utils;

import com.project.ProjectShop.ProjectShop.entities.Customer;
import com.project.ProjectShop.ProjectShop.entities.OrderProduct;

import java.util.List;

public class Cart {

    private Long id;
    private Double totalPrice;

    private Customer customer;

    private List<OrderProduct> orderProducts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", customer=" + customer +
                ", orderProducts=" + orderProducts +
                '}';
    }
}
