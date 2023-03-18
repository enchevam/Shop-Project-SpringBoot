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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


@Service

public class OrderService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    public void createOrder(Order order) {

        // Set current date and time for the order
        order.setOrderDate(new Date());

        // Get a list of all employees and choose one randomly
        List<Employee> employees = employeeRepository.findAll();
        Random random = new Random();
        Employee employee = employees.get(random.nextInt(employees.size()));
        order.setEmployee(employee);

        // Set the order status to PENDING
        order.setOrderStatus(OrderStatus.PENDING);

        // Save the order
        orderRepository.save(order);
    }

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

    public List<Order> getOrdersByCustomer(Customer customer) {
        return orderRepository.findByCustomerOrderByOrderDateDesc(customer);
    }


    private BigDecimal calculateTotalPrice(List<OrderProduct> orderProducts) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderProduct orderProduct : orderProducts) {
            BigDecimal productPrice = orderProduct.getProduct().getPrice();
            BigDecimal quantity = BigDecimal.valueOf(orderProduct.getQuantity());
            totalPrice = totalPrice.add(productPrice.multiply(quantity));
        }
        return totalPrice;
    }
}
