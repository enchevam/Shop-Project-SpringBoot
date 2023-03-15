package com.project.ProjectShop.ProjectShop.unit;

import com.project.ProjectShop.ProjectShop.entities.OrderProduct;
import com.project.ProjectShop.ProjectShop.entities.Product;
import com.project.ProjectShop.ProjectShop.services.CartService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


public class CartServiceTest {
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Mock
    private OrderProduct orderProduct;
    @InjectMocks
    private CartService cartService;

    @Test
    public void findCartByProduct() {

        Product product = new Product(1l, 10);
        List<OrderProduct> orderProducts = Arrays.asList(new OrderProduct(1l, 10,product));
        when(orderProduct.getProduct().getId().equals(product.getId())).thenReturn(true);
        OrderProduct actual = cartService.findCartItemByProduct(orderProducts, product);
        OrderProduct expected = new OrderProduct(1l, 10, new Product());

        assertEquals(actual, expected);
//    public OrderProduct findCartItemByProduct(List<OrderProduct> items, Product product) {
//        for (OrderProduct item : items) {
//            if (item.getProduct().getId().equals(product.getId())) {
//                return item;
//            }
//        }
//
//        return null;
//    }

    }
}
