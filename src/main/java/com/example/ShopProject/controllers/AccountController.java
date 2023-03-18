package com.example.ShopProject.controllers;

import com.example.ShopProject.entities.Customer;
import com.example.ShopProject.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/shop")
public class AccountController {

    @Autowired
    private AuthenticationService authService;

    @GetMapping("/accountDetails")
    public String accountDetails(Model model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer != null) {
            model.addAttribute("customer", customer);
            return "shop/accountDetails";
        } else {
            return "redirect:/shop/customerLogin";
        }
    }

    @PostMapping("/updateAddress")
    public String updateAddress(@RequestParam("address") String address, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        customer.setAddress(address);
        // save updated customer object to database
        return "redirect:/shop/all";
    }

}
