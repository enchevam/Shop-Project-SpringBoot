package com.example.ShopProject.controllers;

import com.example.ShopProject.entities.Product;
import com.example.ShopProject.services.ProductService;
import com.example.ShopProject.utils.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/shop")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/addProduct")
    public String showAddProductForm(Model model) {
        model.addAttribute("productTypes", ProductType.values());
        model.addAttribute("product", new Product());
        return "shop/addProduct";
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute("product") @Valid Product product,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("productTypes", ProductType.values());
            return "shop/addProduct";
        }
        productService.addProduct(product);
        return "redirect:/shop/products";
    }


    @GetMapping("/products")
    public String getProducts(@RequestParam(defaultValue = "name") String sortBy,
                              @RequestParam(defaultValue = "asc") String sortDirection,
                              Model model) {
        // get sorted data based on sortBy and sortDirection
        List<Product> products = productService.getSortedProducts(sortBy, sortDirection);

        // add sorted data to model
        model.addAttribute("products", products);
        model.addAttribute("sortDirection", sortDirection);

        return "shop/products";
    }
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
