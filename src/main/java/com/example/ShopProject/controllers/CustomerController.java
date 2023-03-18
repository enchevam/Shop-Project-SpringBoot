package com.example.ShopProject.controllers;

import com.example.ShopProject.entities.Customer;
import com.example.ShopProject.entities.Order;
import com.example.ShopProject.services.CustomerService;
import com.example.ShopProject.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/shop")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "shop/register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute @Valid Customer customer, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "shop/register";
        }
        try {
            customerService.register(customer);
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "shop/register";
        }

        return "redirect:/shop/customerLogin";
    }

    @GetMapping("/customerOrders")
    public String customerOrders(Model model, HttpSession session) {
        Customer loggedInCustomer = (Customer) session.getAttribute("customer");
        List<Order> customerOrders = orderService.getOrdersByCustomer(loggedInCustomer);
        model.addAttribute("customerOrders", customerOrders);
        return "shop/customerOrders";
    }

}
