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

import javax.servlet.http.HttpServletRequest;
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


    @GetMapping("/add")
    public String addOrder(Model model, HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes, Principal p) {
        Customer customer = (Customer) session.getAttribute("customer");
        /*try {
            customer = authenticationService.takeCustomerSession(session);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }*/

        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null || cart.getOrderProducts() == null || cart.getOrderProducts().isEmpty()) {
            cart = new Cart();
            System.out.println("Order - cart empty 2" + cart);
            redirectAttributes.addFlashAttribute("message", "You have to add products!!!");
            return "redirect:/cart";
        }
        model.addAttribute("cart", cart);
        model.addAttribute("customer", customer);
        model.addAttribute("cartItems", cart.getOrderProducts());

        return "shop/addOrder";
    }

    @PostMapping("/addOrder")
    public String addOrder(HttpSession session, RedirectAttributes redirectAttributes) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getOrderProducts() == null || cart.getOrderProducts().isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "ShoppingCart is empty");
            return "redirect:/cart";
        }

        try {
            orderService.saveOrder(cart, session);
            session.removeAttribute("cart");
            redirectAttributes.addFlashAttribute("message", "Order has been saved!");
            return "redirect:/shop/all";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/cart";
        }
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

    @GetMapping("/orders/details/{id}")
    public String getOrderDetails(@PathVariable("id") Long id, Model model) {
        Order order = orderService.getOrderById(id);
        if (order == null || order.getOrderProducts().isEmpty()) {
            return "redirect:/shop/orders";
        }
        model.addAttribute("order", order);
        return "shop/orderDetails";
    }


}