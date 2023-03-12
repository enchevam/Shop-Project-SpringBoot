package com.example.ShopProject.controllers;

import com.example.ShopProject.entities.Product;
import com.example.ShopProject.services.ProductService;
import com.example.ShopProject.utils.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/shop")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/addProduct")
    public String showAddProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("productTypes", ProductType.values());
        return "shop/addProduct";
    }

    @PostMapping("/addProduct")
    public ModelAndView addProduct(@ModelAttribute("product") @Valid Product product,
                                   BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        if (result.hasErrors()) {
            modelAndView.addObject("productTypes", ProductType.values());
            modelAndView.setViewName("shop/addProduct");
        } else {
            productService.addProduct(product);
            modelAndView.setViewName("redirect:/shop/products");
        }
        return modelAndView;
    }
    @GetMapping("/products")
    public String getProducts(@RequestParam(defaultValue = "name") String sortBy,
                              @RequestParam(defaultValue = "asc") String sortDirection,
                              Model model) {
        List<Product> products = productService.getSortedProducts(sortBy, sortDirection);

        model.addAttribute("products", products);
        model.addAttribute("sortDirection", sortDirection);

        return "shop/products";
    }
    @PostMapping("/delete/{productId}")
    private ModelAndView deleteProduct(@PathVariable(name="productId") Long productId) {
        productService.deleteProduct(productId);
        return new ModelAndView("redirect:/shop/products");
    }


    // Променя продукт по дадено id
    @GetMapping("/editProduct/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("productTypes", ProductType.values());
        return "shop/editProduct";
    }

    @PostMapping("/editProduct/update/{id}")
    public ModelAndView updateProduct(@PathVariable("id") Long id, @ModelAttribute("product") Product product) {
        productService.updateProduct(id, product);

        ModelAndView mav = new ModelAndView("redirect:/shop/products");
        return mav;
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
