package com.example.ShopProject.controllers;

import com.example.ShopProject.entities.Customer;
import com.example.ShopProject.entities.Order;
import com.example.ShopProject.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
public class CheckoutController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/checkout")
    public String checkout(@ModelAttribute("order") Order order, HttpSession session, RedirectAttributes redirectAttributes) {
        Customer loggedInCustomer = (Customer) session.getAttribute("customer");
        order.setCustomer(loggedInCustomer);
        orderService.createOrder(order);
        redirectAttributes.addAttribute("orderId", order.getId());
        return "redirect:/shop/customerOrders";
    }


}
