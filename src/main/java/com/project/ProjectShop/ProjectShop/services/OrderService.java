package com.project.ProjectShop.ProjectShop.services;


import com.project.ProjectShop.ProjectShop.constants.OrderStatus;
import com.project.ProjectShop.ProjectShop.entities.*;
import com.project.ProjectShop.ProjectShop.repositories.*;
import com.project.ProjectShop.ProjectShop.utils.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

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
        order.setOrderStatus(OrderStatus.valueOf("NEW"));
        order.setOrderDate(new Date());
        order.setTotalPrice(cart.getTotalPrice());
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
        orderRepository.save(order);
        order.setOrderProducts(orderProductsChecked);
        orderProductRepository.saveAll(orderProductsChecked);
    }

//    public void saveOrder(Cart cart, HttpSession session) {
//        Customer customer = (Customer) session.getAttribute("customer");
//        Order order = new Order();
//        order.setCustomer(customer);
//        order.setOrderStatus(OrderStatus.valueOf("NEW"));
//        order.setOrderDate(new Date());
//        order.setTotalPrice(cart.getTotalPrice());
//        orderRepository.save(order);
//
//        for (OrderProduct orderProduct : cart.getOrderProducts()) {
//            orderProduct.setOrder(order);
//        }
//
//        orderProductRepository.saveAll(cart.getOrderProducts());
//    }
//
//    public boolean updateQuantity(Cart cart) {
//        List<Product> checkedProduct =new ArrayList<>();
//        for (OrderProduct orderProduct : cart.getOrderProducts()) {
//            Product product = orderProduct.getProduct();
//            if (product.getQuantity() >= orderProduct.getQuantity()) {
//                product.setQuantity(product.getQuantity() - orderProduct.getQuantity());
//                productRepository.save(product);
//                return true;
//            }
//
//        }
//
//        return false;
//    }

}