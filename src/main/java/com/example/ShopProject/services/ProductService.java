package com.example.ShopProject.services;

import com.example.ShopProject.entities.Product;
import com.example.ShopProject.repositories.ProductRepository;
import com.example.ShopProject.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> searchProductsByName(String searchQuery) {
        return productRepository.findByNameContainingIgnoreCase(searchQuery);
    }

    public List<Product> filterProductsByPriceRange(double minPrice, double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public List<Product> filterProductsByQuantity(int quantity) {
        return productRepository.findByQuantityBetween(quantity);
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, Product productDetails) {
        Product product = getProductById(productId);

        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        product.setType(productDetails.getType());
        product.setColor(productDetails.getColor());
        product.setExpireIn(productDetails.getExpireIn());

        return productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        Product product = getProductById(productId);
        productRepository.delete(product);
    }

    public List<Product> sortProductsByName() {
        return productRepository.findAll(Sort.by("name"));
    }

    public List<Product> sortProductsByPrice() {
        return productRepository.findAll(Sort.by("price"));
    }

    public List<Product> sortProductsByExpiresIn() {
        return productRepository.findAll(Sort.by("expiresIn"));
    }

    public List<Product> getExpiringProducts() {
        LocalDate today = LocalDate.now();
        return productRepository.findByExpiresInAfter(today);
    }
}
