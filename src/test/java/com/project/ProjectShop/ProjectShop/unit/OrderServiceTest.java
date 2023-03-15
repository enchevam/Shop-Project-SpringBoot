package com.project.ProjectShop.ProjectShop.unit;

import com.project.ProjectShop.ProjectShop.constants.OrderStatus;
import com.project.ProjectShop.ProjectShop.entities.Customer;
import com.project.ProjectShop.ProjectShop.entities.Order;
import com.project.ProjectShop.ProjectShop.entities.OrderProduct;
import com.project.ProjectShop.ProjectShop.entities.Product;
import com.project.ProjectShop.ProjectShop.repositories.OrderProductRepository;
import com.project.ProjectShop.ProjectShop.repositories.OrderRepository;
import com.project.ProjectShop.ProjectShop.repositories.ProductRepository;
import com.project.ProjectShop.ProjectShop.services.OrderService;
import com.project.ProjectShop.ProjectShop.utils.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderServiceTest {
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderProductRepository orderProductRepository;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void getAllOrders() {
        List<Order> orderList = Arrays.asList(new Order(new Date(), 20d), new Order(new Date(), 23d), new Order(new Date(), 25d));
        when(orderRepository.findAll()).thenReturn(orderList);
        List<Order> actual = orderService.getAllOrders();
        int expected = 3;
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
    public void testCheckOrderProductQuantity(){
//        Customer customerTest = new Customer("name");
//        Cart cart = new Cart(5d, customerTest);
//        Order order = new Order();
//        order.setCustomer(customerTest);
//        order.setOrderStatus(OrderStatus.valueOf("NEW"));
//        order.setOrderDate(new Date());
//        order.setTotalPrice(cart.getTotalPrice());
//
//        Product product = new Product(1l,12);
//        Product product2 = new Product(2l,10);
//        OrderProduct orderProduct = new OrderProduct(4l,12,product);
//        Long productId = 1l;
//        Optional<Product> products = Optional.of(new Product(1l,12));
//
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//
//        List<OrderProduct> orderProducts = Arrays.asList(new OrderProduct(1l,5,product),new OrderProduct(2l,7,product2));
//        cart.setOrderProducts(orderProducts);
//
//
//        List<OrderProduct> actual = orderService.checkProductQuantity(cart, order);
//        assertEquals(orderProducts,actual);
//        for (OrderProduct orderProduct : cart.getOrderProducts()) {
//            Product product = productRepository.findById(orderProduct.getProduct().getId()).get();
//            if (product.getQuantity() < orderProduct.getQuantity()) {
//                throw new RuntimeException("Product out of stock: " + product.getName());
//            }
//            product.setQuantity(product.getQuantity() - orderProduct.getQuantity());
//            productRepository.save(product);
//            orderProduct.setOrder(order);
//            orderProductsChecked.add(orderProduct);
//        }
//        return orderProductsChecked;
    }

//    public void testSaveOrder() {
//        HttpSession session = (HttpSession) new Customer("Name");
//        Customer customerTest = session.getAttribute();
//        Cart cart = new Cart(5d, customerTest);
//        Order order = new Order();
//        order.setCustomer(customerTest);
//        order.setOrderStatus(OrderStatus.valueOf("NEW"));
//        order.setOrderDate(new Date());
//        order.setTotalPrice(cart.getTotalPrice());
//        List<OrderProduct> orderProductsChecked = new ArrayList<>();
//        when(orderService.checkProductQuantity(cart, order)).thenReturn(orderProductsChecked);
//        when(orderRepository.save(order)).thenReturn(order);
//        when(orderProductRepository.saveAll(orderProductsChecked)).thenReturn(orderProductsChecked);
//
//    }
}
