package com.example.ShopProject.controllers;

import com.example.ShopProject.entities.Customer;
import com.example.ShopProject.entities.Order;
import com.example.ShopProject.services.CartService;
import com.example.ShopProject.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;


@Controller
public class CheckoutController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;


    /*@PostMapping("/checkout")
    public String checkout(@ModelAttribute("order") Order order, HttpSession session, RedirectAttributes redirectAttributes) {
        Customer loggedInCustomer = (Customer) session.getAttribute("customer");
        order.setCustomer(loggedInCustomer);

        // Calculate total price using the new calculateTotalPrice() method in cartService
        BigDecimal totalPrice = cartService.calculateTotalPrice(order.getOrderProducts());

        // Check if the total price is greater than 0
        if (totalPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Total price should be greater than 0");
        }

        order.setTotalPrice(totalPrice);
        orderService.createOrder(order);
        redirectAttributes.addAttribute("orderId", order.getId());
        return "redirect:/shop/customerOrders";
    }*/
}
