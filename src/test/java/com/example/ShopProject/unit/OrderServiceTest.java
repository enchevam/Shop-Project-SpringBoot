package com.example.ShopProject.unit;


import com.example.ShopProject.entities.Customer;
import com.example.ShopProject.entities.Order;
import com.example.ShopProject.repositories.OrderRepository;
import com.example.ShopProject.services.OrderService;

import com.example.ShopProject.utils.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class OrderServiceTest {
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;


    @Test
    public void getAllOrders() {
        List<Order> orderList = Arrays.asList(new Order(new Date(), 20d), new Order(new Date(), 23d));
        when(orderRepository.findAll()).thenReturn(orderList);
        List<Order> actual = orderService.getAllOrders();
        int expected = 2;
        assertEquals(expected, actual.size());
        verify(orderRepository, atLeastOnce()).findAll();
    }

    @Test
    public void findOrderById() {
        Long orderId = 1l;
        Optional<Order> order = Optional.of(new Order());
        when(orderRepository.findById(orderId)).thenReturn(order);

        Order actual = orderService.getOrderById(orderId);
        Order expected = order.get();

        assertEquals(expected, actual);
        verify(orderRepository, atLeastOnce()).findById(orderId);
    }

    @Test
    public void testSaveOrder() {
        Order order = new Order();
        when(orderRepository.save(order)).thenReturn(order);
        Order result = orderService.saveOrder(order);
        assertNotNull(result);
        verify(orderRepository, times(1)).save(order);
    }
    @Test
    public void testDeleteOrderById() {
        Long orderId = 1L;
        orderService.deleteOrderById(orderId);
        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    public void testGetOrdersByStatusIfNull(){
        List<Order> orders = new ArrayList<>();
        OrderStatus status = null;
        when(orderRepository.findAll()).thenReturn( orders);

        List<Order> actual = orderService.getOrdersByStatus(status);
        List<Order> expected = new ArrayList<>();
        assertEquals(expected, actual);
    }
    @Test
    public void testGetOrdersByStatusIfNotNUll(){
        List<Order> orders = new ArrayList<>();
        OrderStatus status = OrderStatus.valueOf("DELIVERED");
        when(orderRepository.findAll()).thenReturn( orders);

        List<Order> actual = orderService.getOrdersByStatus(status);
        List<Order> expected = orders;
        assertEquals(expected, actual);
    }

    @Test
    public void testGetOrdersByCustomer(){
        Customer customer = new Customer();
        List<Order> orders = new ArrayList<>();
        when(orderRepository.findByCustomer(customer)).thenReturn(orders);
        List<Order> actual = orderService.getOrdersByCustomer(customer);
        assertEquals(orders,actual);
    }

}
