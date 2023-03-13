package com.project.ProjectShop.ProjectShop.controllers;

import com.project.ProjectShop.ProjectShop.entities.OrderProduct;
import com.project.ProjectShop.ProjectShop.repositories.ProductRepository;
import com.project.ProjectShop.ProjectShop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/")
    public String showShop(Model model, String keyword, OrderProduct orderProduct) {
        model.addAttribute("products", productService.findAllAvailableQuantity(keyword));
        //model.addAttribute("type", Type.values());
        model.addAttribute("items", orderProduct.getQuantity());

        return "all";//all_products
    }

}
