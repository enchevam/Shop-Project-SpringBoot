package com.example.ShopProject.unit;


import com.example.ShopProject.entities.*;
import com.example.ShopProject.repositories.OrderProductRepository;
import com.example.ShopProject.repositories.OrderRepository;
import com.example.ShopProject.repositories.ProductRepository;
import com.example.ShopProject.services.OrderService;
import com.example.ShopProject.utils.Cart;
import com.example.ShopProject.utils.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpSession;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderProductRepository orderProductRepository;
    @Mock
    private HttpSession session;


    @InjectMocks
    private OrderService orderService;

    @Test
    public void testUpdateOrderStatus() {// TODO: 22.3.2023 ?.
        Long id = 2l;
        Employee employee = new Employee();
        when(session.getAttribute("employee")).thenReturn(employee);
        Order order = new Order();
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        orderService.updateOrderStatus(id, OrderStatus.valueOf("PENDING"), session);
        verify(orderRepository, atLeastOnce()).save(order);

    }

    @Test
    public void testCheckProductQuantityOutOfStock() { // TODO: 21.3.2023 ?.
        Long id = 1l;
        Product product = new Product();
        product.setName("prod");
        product.setQuantity(8);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        Cart cart = new Cart();
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setQuantity(11);
        List<OrderProduct> orderProducts = new ArrayList<>();
        orderProducts.add(orderProduct);
        cart.setOrderProducts(orderProducts);

        Order order = new Order();

//        Throwable exception = assertThrows(RuntimeException.class, () -> orderService.checkProductQuantity(cart, order));
//        String expected = "The available quantity for: " + product.getName() + " is " + product.getQuantity();
//        assertEquals(expected, exception.getMessage());
//        doThrow(new RuntimeException()).when(orderService).checkProductQuantity(cart, order);
        assertThrows(RuntimeException.class, () -> orderService.checkProductQuantity(cart, order));
    }

    @Test
    public void testCheckProductQuantity() { // TODO: 21.3.2023 ?.
        Long id = 1l;
        Product product = new Product();
        product.setId(id);
        product.setQuantity(10);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        Cart cart = new Cart();
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setQuantity(9);
        List<OrderProduct> orderProducts = new ArrayList<>();
        orderProducts.add(orderProduct);
        cart.setOrderProducts(orderProducts);

        Order order = new Order();
        List<OrderProduct> checked = orderService.checkProductQuantity(cart, order);
        int expected = 1;
        assertEquals(expected, product.getQuantity());
    }

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
    public void testGetOrdersByStatusIfNull() {
        List<Order> orders = new ArrayList<>();
        OrderStatus status = null;
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> actual = orderService.getOrdersByStatus(status);
        List<Order> expected = new ArrayList<>();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetOrdersByStatusIfNotNUll() {
        List<Order> orders = new ArrayList<>();
        OrderStatus status = OrderStatus.valueOf("DELIVERED");
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> actual = orderService.getOrdersByStatus(status);
        List<Order> expected = orders;
        assertEquals(expected, actual);
    }

    @Test
    public void testGetOrdersByCustomer() {
        Customer customer = new Customer();
        List<Order> orders = new ArrayList<>();
        when(orderRepository.findByCustomer(customer)).thenReturn(orders);
        List<Order> actual = orderService.getOrdersByCustomer(customer);
        assertEquals(orders, actual);
    }


}
