package com.example.ShopProject.services;

import com.example.ShopProject.entities.Product;
import com.example.ShopProject.repositories.ProductRepository;
import com.example.ShopProject.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getSortedProducts(String sortBy, String sortDirection) {
        List<Product> products = productRepository.findAll();

        Comparator<Product> comparator = null;

        switch (sortBy) {
            case "name":
                comparator = Comparator.comparing(Product::getName);
                break;
            case "price":
                comparator = Comparator.comparing(Product::getPrice);
                break;
            case "expire date":
                comparator = Comparator.comparing(Product::getExpireIn);
                break;
        }

        if (comparator != null) {
            if ("desc".equals(sortDirection)) {
                comparator = comparator.reversed();
            }
            products.sort(comparator);
        }
        return products;
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    public Product updateProduct(Long id, Product product) {
        if (!id.equals(product.getId())) {
            product.setId(id);
        }

        return productRepository.save(product);
    }

    public List<Product> filterProductsByPriceRange(double minPrice, double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public List<Product> filterProductsByQuantity(int quantity) {
        return productRepository.findByQuantity(quantity);
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
    }


    public List<Product> getExpiringProducts() {
        LocalDate today = LocalDate.now();
        return productRepository.findByExpireInAfter(today);
    }

}
