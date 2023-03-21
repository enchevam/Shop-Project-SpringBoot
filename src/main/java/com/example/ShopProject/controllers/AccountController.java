package com.example.ShopProject.controllers;

import com.example.ShopProject.entities.Customer;
import com.example.ShopProject.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/shop")
public class AccountController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/accountDetails")
    public String accountDetails(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer != null) {
            model.addAttribute("customer", customer);
            return "shop/address_up";
        } else {
            redirectAttributes.addFlashAttribute("message", "You are not logged in");
            return "redirect:/shop/all";
        }
    }
    @PostMapping("/updateAddress")
    public String saveCustomerForm( @Valid Customer customer,  BindingResult bindingResult,  Model model, HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "/shop/address_up";
        }
        customerService.updateCustomerInformation(customer);
        Customer updatedCustomer = customerService.findById(customer.getId());
        session.setAttribute("customer", updatedCustomer);
        model.addAttribute("message", "Your information has been updated successfully!!!");
        return "/shop/address_up";
    }

}
