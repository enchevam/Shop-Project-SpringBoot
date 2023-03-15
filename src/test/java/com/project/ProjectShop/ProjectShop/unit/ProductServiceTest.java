package com.project.ProjectShop.ProjectShop.unit;

import com.project.ProjectShop.ProjectShop.entities.Product;
import com.project.ProjectShop.ProjectShop.repositories.ProductRepository;
import com.project.ProjectShop.ProjectShop.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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

        Product actual = productService.findById(productId);
        Product expected = product.get();

        assertEquals(expected, actual);
    }

    @Test
    public void findAllAvailableQuantityWithKeyword() {
        String keyword = "product";
        List<Product> productList = Arrays.asList(new Product("product1", 1), new Product("product2", 0), new Product("product3", 3));
        when(productRepository.search(keyword)).thenReturn(productList);

        List<Product> result = productService.findAllAvailableQuantity(keyword);
        int expected = 2;

        assertEquals(2, result.size());
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