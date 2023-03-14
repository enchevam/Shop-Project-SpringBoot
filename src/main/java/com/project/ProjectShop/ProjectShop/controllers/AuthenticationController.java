package com.project.ProjectShop.ProjectShop.controllers;



import com.project.ProjectShop.ProjectShop.entities.Customer;
import com.project.ProjectShop.ProjectShop.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;


@Controller
@RequestMapping("/shop")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authService;

    @GetMapping
    public String getIndexMenu() {
        return "shop/index";
    }

//    @GetMapping("/employeeLogin")
//    public String showEmployeeLoginForm(Model model) {
//        model.addAttribute("employee", new Employee());
//        return "shop/employeeLogin";
//    }

    @GetMapping("/customerLogin")
    public String showCustomerLoginForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "shop/customerLogin";
    }

//    @PostMapping("/employeeLogin")
//    public String processEmployeeLoginForm(@ModelAttribute("employee") Employee employee,
//                                           HttpSession session,
//                                           Model model) {
//        Optional<Employee> authenticatedEmployee = authService.authenticateEmployee(employee.getId(), employee.getPassword());
//
//        if (authenticatedEmployee.isPresent()) {
//            session.setAttribute("employee", authenticatedEmployee.get());
//            return "redirect:/shop/home";
//        } else {
//            model.addAttribute("error", "Invalid username or password");
//            return "shop/employeeLogin";
//        }
//    }

    @PostMapping("/customerLogin")
    public String login(@ModelAttribute("customer") Customer customer, HttpSession session, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "shop/customerLogin";
        }

        try {
            Optional<Customer> authenticatedCustomer = authService.authenticateCustomer(customer.getEmail(), customer.getPassword());

            if (authenticatedCustomer.isPresent()) {
                session.setAttribute("customer", authenticatedCustomer.get());
                return "redirect:/";///shop/products
            } else {
                model.addAttribute("customer", customer);
                model.addAttribute("error", "Wrong email or password");
                return "shop/customerLogin";
            }
        } catch (IllegalArgumentException ex) {
            bindingResult.rejectValue("email", "error.email", ex.getMessage());
            return "shop/customerLogin";
        }
    }

//    @GetMapping("/home")
//    public String showHome(HttpSession session, Model model) {
//        Employee employee = (Employee) session.getAttribute("employee");
//        if (employee == null) {
//            return "redirect:/shop/employeeLogin";
//        } else {
//            model.addAttribute("employee", employee);
//            return "/shop/home";
//        }
//    }


}