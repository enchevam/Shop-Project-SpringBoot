package com.example.ShopProject.controllers;

import com.example.ShopProject.entities.OrderProduct;
import com.example.ShopProject.entities.Product;
import com.example.ShopProject.services.ProductService;
import com.example.ShopProject.utils.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private ModelAndView deleteProduct(@PathVariable(name = "productId") Long productId) {
        productService.deleteProduct(productId);
        return new ModelAndView("redirect:/shop/products");
    }

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

    @PostMapping("/products")
    public String searchProducts(
            @RequestParam(name = "searchById", required = false) Long searchById,
            @RequestParam(name = "searchByName", required = false) String searchByName,
            @RequestParam(name = "searchByQuantity", required = false) Integer searchByQuantity,
            @RequestParam(name = "price-min", required = false) BigDecimal priceMin,
            @RequestParam(name = "price-max", required = false) BigDecimal priceMax,
            Model model) {

        if (priceMin != null) {
            priceMin = priceMin.setScale(2, RoundingMode.HALF_UP);
        }
        if (priceMax != null) {
            priceMax = priceMax.setScale(2, RoundingMode.HALF_UP);
        }

        List<Product> products = productService.searchProducts(searchById, searchByName, searchByQuantity, priceMin, priceMax);

        model.addAttribute("products", products);
        return "shop/products";
    }
    @GetMapping("/all")
    public String showShop(Model model, String keyword, OrderProduct orderProduct) {
        List<Product> products = productService.findAllAvailableQuantity(keyword);
        model.addAttribute("products", products);
        model.addAttribute("items", orderProduct.getQuantity());
        return "shop/all";
    }

}
