package com.example.ShopProject.controllers;

import com.example.ShopProject.entities.*;
import com.example.ShopProject.services.CustomerService;
import com.example.ShopProject.services.OrderService;
import com.example.ShopProject.utils.Cart;
import com.example.ShopProject.utils.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/shop")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;


   /* @GetMapping("/addOrder")
    public String showAddOrderPage(Model model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null || cart.getOrderProducts() == null || cart.getOrderProducts().size() == 0) {
            cart = new Cart();
            System.out.println("Order - cart empty 2" + cart);
            model.addAttribute("message", "You have to add products!!!");
            return "redirect:/shop/all";
        }

        model.addAttribute("cart", cart);
        model.addAttribute("customer", customer);
        model.addAttribute("cartItems", cart.getOrderProducts());

        return "shop/shCart";
    }*/

   /* @PostMapping("/addOrder")
    public String addOrder(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        Customer customer = (Customer) session.getAttribute("customer");

        if (cart == null || cart.getOrderProducts() == null || cart.getOrderProducts().isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Shopping Cart is empty");
            return "redirect:/shop/shCart";
        }

        try {
            orderService.saveOrder(cart, session);
            session.removeAttribute("cart");
            List<Order> orders = orderService.getOrdersByCustomer(customer);
            model.addAttribute("orders", orders);
            redirectAttributes.addFlashAttribute("message", "Order has been saved!");
            return "redirect:/shop/customerOrders";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/shop/shCart";
        }
    }*/


    @GetMapping("/customerOrders")
    public String showCustomerOrders(Model model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        List<Order> orders = orderService.getOrdersByCustomer(customer);
        model.addAttribute("orders", orders);
        return "shop/customerOrders";
    }


    @GetMapping("/orders")
    public String showOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        model.addAttribute("statuses", OrderStatus.values());
        return "shop/orders";
    }

    @PostMapping("/orders/filter")
    public String filterOrders(@RequestParam("status") String status, Model model) {
        List<Order> orders;
        if (status.equals("ALL")) {
            orders = orderService.getAllOrders();
        } else {
            OrderStatus orderStatus = OrderStatus.valueOf(status);
            orders = orderService.getOrdersByStatus(orderStatus);
        }
        model.addAttribute("orders", orders);
        model.addAttribute("statuses", OrderStatus.values());
        return "shop/orders";
    }

    @PostMapping("/orders/{id}/status")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam(name = "orderStatus") OrderStatus status, Model model) {
        orderService.updateOrderStatus(id, status);
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "redirect:/shop/orders";
    }


}