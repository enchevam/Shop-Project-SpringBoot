package com.example.ShopProject.unit;


import com.example.ShopProject.entities.OrderProduct;
import com.example.ShopProject.entities.Product;
import com.example.ShopProject.repositories.ProductRepository;
import com.example.ShopProject.services.CartService;
import com.example.ShopProject.services.ProductService;
import com.example.ShopProject.utils.Cart;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


public class CartServiceTest {
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private HttpSession session;
    @Mock
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private CartService cartService;


    @Test
    public void testFindCartItemByProduct() {
        Product product = new Product();
        product.setId(1L);
        OrderProduct item1 = new OrderProduct();
        item1.setProduct(product);
        item1.setQuantity(1);
        OrderProduct item2 = new OrderProduct();
        item2.setProduct(product);
        item2.setQuantity(2);
        List<OrderProduct> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        OrderProduct actual = cartService.findCartItemByProduct(items, Optional.of(product));
        assertNotNull(actual);
        assertEquals(item1.getProduct().getId(), actual.getProduct().getId());
    }

    @Test
    public void testCalculateTotalPrice() {
        Product product1 = new Product();
        product1.setPrice(BigDecimal.valueOf(10));
        Product product2 = new Product();
        product2.setPrice(BigDecimal.valueOf(20));

        OrderProduct item1 = new OrderProduct();
        item1.setProduct(product1);
        item1.setQuantity(1);
        OrderProduct item2 = new OrderProduct();
        item2.setProduct(product2);
        item2.setQuantity(2);

        List<OrderProduct> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        BigDecimal actual = cartService.calculateTotalPrice(items);
        BigDecimal expected = BigDecimal.valueOf(50).setScale(2, RoundingMode.HALF_UP);
        expected.setScale(2, RoundingMode.HALF_UP);

        assertEquals(expected, actual);
    }

    @Test
    void testRemoveItemFromShoppingCart() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setPrice(BigDecimal.valueOf(10.0));
        product1.setQuantity(5);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setPrice(BigDecimal.valueOf(20.0));
        product2.setQuantity(10);

        OrderProduct orderProduct1 = new OrderProduct();
        orderProduct1.setProduct(product1);
        orderProduct1.setQuantity(2);

        OrderProduct orderProduct2 = new OrderProduct();
        orderProduct2.setProduct(product2);
        orderProduct2.setQuantity(1);

        List<OrderProduct> items = new ArrayList<>();
        items.add(orderProduct1);
        items.add(orderProduct2);

        Cart cart = new Cart();
        cart.setOrderProducts(items);

        cartService.removeItemFromShoppingCart(1L, cart);

        assertEquals(1, cart.getOrderProducts().size());
        assertNull(cartService.findCartItemByProduct(cart.getOrderProducts(), Optional.of(product1)));
        assertNotNull(cartService.findCartItemByProduct(cart.getOrderProducts(), Optional.of(product2)));
        assertEquals(BigDecimal.valueOf(20).setScale(2), cart.getTotalPrice());
    }

    @Test
    public void testAddItemToShoppingCart() {

        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setPrice(BigDecimal.valueOf(10).setScale(2));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        Cart cart = new Cart();
        List<OrderProduct> items = new ArrayList<>();
        cart.setOrderProducts(items);
        when(session.getAttribute("cart")).thenReturn(cart);

        cartService.addItemToShoppingCart(productId, session);

        assertEquals(productId, cart.getOrderProducts().size());

        OrderProduct item = cart.getOrderProducts().get(0);

        assertEquals(product, item.getProduct());
        assertEquals(product.getPrice(), cart.getTotalPrice());
    }

    @Test
    public void testUpdateItemQuantityInShoppingCart() {
        Long productId = 1L;
        Integer quantity = 3;
        Product product = new Product();
        product.setId(productId);
        product.setPrice(BigDecimal.valueOf(10d));

        OrderProduct item = new OrderProduct();
        item.setProduct(product);
        item.setQuantity(1);

        List<OrderProduct> items = new ArrayList<>();
        items.add(item);

        Cart cart = new Cart();
        cart.setOrderProducts(items);
        cart.setTotalPrice(product.getPrice());
        when(session.getAttribute("cart")).thenReturn(cart);

        cartService.updateItemQuantityInShoppingCart(productId, quantity, session);

        assertEquals(quantity, item.getQuantity());
    }
}
