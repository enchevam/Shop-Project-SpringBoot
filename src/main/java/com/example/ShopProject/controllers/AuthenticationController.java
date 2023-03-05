package com.example.ShopProject.controllers;

import com.example.ShopProject.entities.Employee;
import com.example.ShopProject.repositories.EmployeeRepository;
import com.example.ShopProject.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/employeeLogin")
    public String showLoginForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "shop/employeeLogin";
    }

    @PostMapping("/employeeLogin")
    public String processLoginForm(@ModelAttribute("employee") Employee employee,
                                   HttpSession session,
                                   Model model) {
        Optional<Employee> authenticatedEmployee = authService.authenticateEmployee(employee.getId(), employee.getPassword());

        if (authenticatedEmployee.isPresent()) {
            session.setAttribute("employee", authenticatedEmployee.get());
            return "redirect:/shop/home";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "shop/employeeLogin";
        }
    }

    /*@GetMapping("/home")
    public String showHome(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/shop/employeeLogin";
        } else {
            model.addAttribute("employee", employee);
            return "/shop/home";
        }
    }*/

    @GetMapping("/home")
    public String showHome(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/shop/employeeLogin";
        } else {
            model.addAttribute("employee", employee);
            return "/shop/home";
        }
    }





}