package com.example.ShopProject.controllers;

import com.example.ShopProject.entities.Employee;
import com.example.ShopProject.entities.Order;
import com.example.ShopProject.services.EmployeeService;
import com.example.ShopProject.services.OrderService;
import com.example.ShopProject.utils.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/shop")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/employees")
    public String getEmployees(@RequestParam(defaultValue = "name") String sortBy,
                               @RequestParam(defaultValue = "asc") String sortDirection,
                               Model model) {
        List<Employee> employees = employeeService.getSortedEmployees(sortBy, sortDirection);

        model.addAttribute("employees", employees);
        model.addAttribute("sortDirection", sortDirection);

        return "shop/employees";
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
    public String updateOrderStatus(@PathVariable Long id, @RequestParam(name = "orderStatus") OrderStatus status, Model model, HttpSession session) {
        orderService.updateOrderStatus(id, status, session);
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "redirect:/shop/orders";
    }

    @GetMapping("/orders/details/{id}")
    public String getOrderDetails(@PathVariable("id") Long id, Model model) {
        Order order = orderService.getOrderById(id);
        if (order == null || order.getOrderProducts().isEmpty()) {
            return "redirect:/orders";
        }
        model.addAttribute("order", order);
        model.addAttribute("customer", order.getCustomer());
        return "shop/orderDetails";
    }
}
