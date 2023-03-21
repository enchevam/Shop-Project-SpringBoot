package com.example.ShopProject.unit;

import com.example.ShopProject.entities.Product;
import com.example.ShopProject.repositories.ProductRepository;
import com.example.ShopProject.services.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void findProductById() {
        Long productId = 1l;
        Optional<Product> product = Optional.of(new Product());
        when(productRepository.findById(productId)).thenReturn(product);

        Product actual = productService.getProductById(productId);
        Product expected = product.get();

        assertEquals(expected, actual);
    }

    @Test
    public void findAllAvailableQuantityWithKeyword() {
        String keyword = "product";
        List<Product> productList = Arrays.asList(new Product("product1", 1), new Product("product2", 0), new Product("product3", 3));
        when(productRepository.search(keyword)).thenReturn(productList);

        List<Product> actual = productService.findAllAvailableQuantity(keyword);
        int expected = 2;

        assertEquals(expected, actual.size());
    }

    @Test
    public void findAllAvailableQuantityWithoutKeyword() {
        List<Product> productList = Arrays.asList(new Product("product1", 1), new Product("product2", 0), new Product("product3", 3));
        when(productRepository.findAll()).thenReturn(productList);

        List<Product> actual = productService.findAllAvailableQuantity(null);
        int expected = 2;

        assertEquals(expected, actual.size());
    }

}