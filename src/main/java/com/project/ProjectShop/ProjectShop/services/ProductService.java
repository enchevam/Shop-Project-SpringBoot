package com.project.ProjectShop.ProjectShop.services;

import com.project.ProjectShop.ProjectShop.entities.Product;
import com.project.ProjectShop.ProjectShop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;


    public void save(Product product) {
        productRepository.save(product);
    }

    public Product findById(Long productId) {
        return productRepository.findById(productId).get();
    }

    public List<Product> findAllAvailableQuantity(String keyword) {
        List<Product> prod = new ArrayList<>();
        if (keyword != null) {
            for (Product pr : productRepository.search(keyword))
                if (pr.getQuantity() > 0) {
                    prod.add(pr);
                }
            return prod;
        }

        for (Product pr : productRepository.findAll()) {
            if (pr.getQuantity() > 0) {
                prod.add(pr);
            }
        }
        return prod;
    }


}
