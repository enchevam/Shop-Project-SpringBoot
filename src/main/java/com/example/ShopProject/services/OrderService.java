package com.example.ShopProject.services;

import com.example.ShopProject.entities.*;
import com.example.ShopProject.repositories.*;
import com.example.ShopProject.utils.Cart;
import com.example.ShopProject.utils.OrderStatus;
import com.example.ShopProject.utils.exceptions.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

@Service

public class OrderService {
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).get();
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

    public void saveOrder(Cart cart, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderStatus(OrderStatus.valueOf("PENDING"));
        order.setOrderDate(new Date());
        order.setTotalPrice(cart.getTotalPrice());
        List<OrderProduct> orderProductsChecked = checkProductQuantity(cart,order);

        orderRepository.save(order);
        order.setOrderProducts(orderProductsChecked);
        orderProductRepository.saveAll(orderProductsChecked);
    }
    public List<OrderProduct> checkProductQuantity(Cart cart, Order order){
        List<OrderProduct> orderProductsChecked = new ArrayList<>();
        for (OrderProduct orderProduct : cart.getOrderProducts()) {
            Product product = productRepository.findById(orderProduct.getProduct().getId()).get();
            if (product.getQuantity() < orderProduct.getQuantity()) {
                throw new RuntimeException("Product out of stock: " + product.getName());
            }
            product.setQuantity(product.getQuantity() - orderProduct.getQuantity());
            productRepository.save(product);
            orderProduct.setOrder(order);
            orderProductsChecked.add(orderProduct);
        }
        return orderProductsChecked;
    }


    public List<Order> getAllOrdersSortedByDate() {
        return orderRepository.findAll(Sort.by(Sort.Direction.DESC, "orderDate"));
    }


    public List<Order> getOrdersByStatus(OrderStatus status) {
        if (status == null) {
            return orderRepository.findAll();
        } else {
            return orderRepository.getOrdersByStatus(status);
        }
    }

    @Transactional
    public void updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        order.setStatus(status);
        orderRepository.save(order);
    }

    public void addOrder(Order order, String customerEmail) {
        Customer customer = customerRepository.findByEmail(customerEmail);
        order.setCustomer(customer);
        orderRepository.save(order);
    }

    public List<Order> getOrdersByCustomerEmail(String email) {
        Customer customer = customerRepository.findByEmail(email);
        return orderRepository.findByCustomer(customer);
    }


}
