package com.project.ProjectShop.ProjectShop.controllers;

import com.project.ProjectShop.ProjectShop.entities.*;
import com.project.ProjectShop.ProjectShop.repositories.*;
import com.project.ProjectShop.ProjectShop.services.OrderService;
import com.project.ProjectShop.ProjectShop.utils.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;


    @GetMapping("/add")
    public String addOrder(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Customer customer = (Customer) session.getAttribute("customer");
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null || cart.getOrderProducts() == null || cart.getOrderProducts().size() == 0) {
            cart = new Cart();
            System.out.println("Order - cart empty 2" + cart);
            redirectAttributes.addFlashAttribute("message", "You have to add products!!!");
            return "redirect:/cart";
        }
        model.addAttribute("cart", cart);
        model.addAttribute("customer", customer);
        model.addAttribute("cartItems", cart.getOrderProducts());

        return "add_order";
    }

    @PostMapping("/addOrder")
    public String addOrder(HttpSession session, RedirectAttributes redirectAttributes) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getOrderProducts() == null || cart.getOrderProducts().size() == 0) {
            redirectAttributes.addFlashAttribute("message", "ShoppingCart is empty");
            return "redirect:/cart";
        }

        try {
            orderService.saveOrder(cart, session);
            session.removeAttribute("cart");
            redirectAttributes.addFlashAttribute("message", "Order has been saved!");
            return "redirect:/";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/cart";
        }
    }

    @GetMapping("/index2")
    public String showForm() {
        return "index2";
    }
}