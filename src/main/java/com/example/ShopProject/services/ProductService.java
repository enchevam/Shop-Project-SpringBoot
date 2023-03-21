package com.example.ShopProject.services;

import com.example.ShopProject.entities.DeletedProduct;
import com.example.ShopProject.entities.Product;
import com.example.ShopProject.repositories.DeletedProductRepository;
import com.example.ShopProject.repositories.ProductRepository;
import com.example.ShopProject.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DeletedProductRepository deletedProductRepository;

    public List<Product> getSortedProducts(String sortBy, String sortDirection) {
        List<Product> products = productRepository.findAll();

        Comparator<Product> comparator = switch (sortBy) {
            case "name" -> Comparator.comparing(Product::getName);
            case "price" -> Comparator.comparing(Product::getPrice);
            case "expireIn" -> Comparator.comparing(Product::getExpireDate);
            default -> null;
        };

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
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product id: " + productId));
        DeletedProduct deletedProduct = new DeletedProduct(product);
        deletedProductRepository.save(deletedProduct);
        productRepository.delete(product);
    }

    public Product updateProduct(Long id, Product product) {
        if (!id.equals(product.getId())) {
            product.setId(id);
        }

        return productRepository.save(product);
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
    }

    public List<Product> searchProducts(Long id, String name, Integer quantity, BigDecimal priceMin, BigDecimal priceMax) {
        if (priceMin != null) {
            priceMin = priceMin.setScale(2, RoundingMode.HALF_UP);
        }
        if (priceMax != null) {
            priceMax = priceMax.setScale(2, RoundingMode.HALF_UP);
        }

        return productRepository.searchProducts(id, name, quantity, priceMin, priceMax);
    }

    public void save(Product product) {
        productRepository.save(product);
    }


    public List<Product> findAllAvailableQuantity(String keyword) {
        List<Product> availableProducts = new ArrayList<>();

        if (keyword != null) {
            List<Product> searchResults = productRepository.search(keyword);

            for (Product product : searchResults) {
                if (product.getQuantity() > 0) {
                    availableProducts.add(product);
                }
            }
        } else {
            List<Product> allProducts = productRepository.findAll();

            for (Product product : allProducts) {
                if (product.getQuantity() > 0) {
                    availableProducts.add(product);
                }
            }
        }

        return availableProducts;
    }
}
