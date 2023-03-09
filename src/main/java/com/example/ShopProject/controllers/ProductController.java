package com.example.ShopProject.controllers;

import com.example.ShopProject.entities.Product;
import com.example.ShopProject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;
    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product newProduct = productService.addProduct(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    // Променя продукт по дадено id
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // Изтрива продукт по дадено id
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Връща всички продукти, сортирани по име
    @GetMapping("/products/sortByName")
    public ResponseEntity<List<Product>> getProductsSortedByName() {
        List<Product> sortedProducts = productService.sortProductsByName();
        return new ResponseEntity<>(sortedProducts, HttpStatus.OK);
    }

    // Връща всички продукти, сортирани по цена
    @GetMapping("/products/sortByPrice")
    public ResponseEntity<List<Product>> getProductsSortedByPrice() {
        List<Product> sortedProducts = productService.sortProductsByPrice();
        return new ResponseEntity<>(sortedProducts, HttpStatus.OK);
    }

    // Връща всички продукти, сортирани по срок на годност (най-скоро изтичащите първи)
    @GetMapping("/products/sortByExpiresIn")
    public ResponseEntity<List<Product>> getProductsSortedByExpiresIn() {
        List<Product> sortedProducts = productService.sortProductsByExpireIn();
        return new ResponseEntity<>(sortedProducts, HttpStatus.OK);
    }

    // Търси продукти по име
    /*@GetMapping("/products/searchByName")
    public ResponseEntity<List<Product>> searchProductsByName(@RequestParam String name) {
        List<Product> foundProducts = productService.searchProductsByName(name);
        return new ResponseEntity<>(foundProducts, HttpStatus.OK);
    }*/

    // Търси продукти по цена (в даден интервал)
    @GetMapping("/products/searchByPrice")
    public ResponseEntity<List<Product>> searchProductsByPrice(@RequestParam double minPrice, @RequestParam double maxPrice) {
        List<Product> foundProducts = productService.filterProductsByPriceRange(minPrice,maxPrice);
        return new ResponseEntity<>(foundProducts, HttpStatus.OK);
    }

    // Търси продукти по налично количество
    @GetMapping("/products/searchByQuantity")
    public ResponseEntity<List<Product>> searchProductsByQuantity(@RequestParam int quantity) {
        List<Product> foundProducts = productService.filterProductsByQuantity(quantity);
        return new ResponseEntity<>(foundProducts, HttpStatus.OK);
    }
}
