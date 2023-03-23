package com.example.ShopProject.unit;

import com.example.ShopProject.entities.Product;
import com.example.ShopProject.repositories.ProductRepository;
import com.example.ShopProject.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
    public void testSaveProduct() {
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);
        productService.save(product);
        verify(productRepository, times(1)).save(product);
    }

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

    @Test
    public void testAddProduct(){
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);
        productService.addProduct(product);
        verify(productRepository,atLeastOnce()).save(product);

    }
    @Test
    public void testGetSortedProductsByNameASC() {// TODO: 21.3.2023 ?.  
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        product1.setName("prod1");
        product2.setName("prod3");
        product3.setName("prod2");
        List<Product> products = Arrays.asList(product1, product2, product3);

        when(productRepository.findAll()).thenReturn(products);

        List<Product> sortedProducts = productService.getSortedProducts("name", "asc");

        assertEquals(product1.getName(), sortedProducts.get(0).getName());
        assertEquals(product3.getName(), sortedProducts.get(1).getName());
        assertEquals(product2.getName(), sortedProducts.get(2).getName());
    }
    @Test
    public void testGetSortedProductsByPriceASC() { // TODO: 21.3.2023 ?.
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        product1.setPrice(BigDecimal.valueOf(2));
        product2.setPrice(BigDecimal.valueOf(3));
        product3.setPrice(BigDecimal.valueOf(1));
        List<Product> products = Arrays.asList(product1, product2, product3);

        when(productRepository.findAll()).thenReturn(products);

        List<Product> sortedProducts = productService.getSortedProducts("price", "asc");

        assertEquals(product3.getPrice(), sortedProducts.get(0).getPrice());
        assertEquals(product1.getPrice(), sortedProducts.get(1).getPrice());
        assertEquals(product2.getPrice(), sortedProducts.get(2).getPrice());
    }
    @Test
    public void testGetSortedProductsByDateASC() { // TODO: 21.3.2023 ?.
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        product1.setExpireDate(LocalDate.of(2023,03,02));
        product2.setExpireDate(LocalDate.of(2023,03,01));
        product3.setExpireDate(LocalDate.of(2023,03,05));
        List<Product> products = Arrays.asList(product1, product2, product3);

        when(productRepository.findAll()).thenReturn(products);

        List<Product> sortedProducts = productService.getSortedProducts("expireIn", "asc");

        assertEquals(product2.getExpireDate(), sortedProducts.get(0).getExpireDate());
        assertEquals(product1.getExpireDate(), sortedProducts.get(1).getExpireDate());
        assertEquals(product3.getExpireDate(), sortedProducts.get(2).getExpireDate());
    }

}