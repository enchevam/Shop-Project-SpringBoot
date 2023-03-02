package com.example.ShopProject.controllers;

import com.example.ShopProject.entities.Employee;
import com.example.ShopProject.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthenticationController {

    @Autowired
    private AuthenticationService authService;

    @GetMapping("/employeeLogin")
    public String showLoginForm() {
        return "employeeLogin";
    }

    @PostMapping("/employeeLogin")
    public String login(@RequestParam Long employeeId,
                        @RequestParam String password,
                        Model model) throws ChangeSetPersister.NotFoundException {
        Optional<Employee> employee = authService.authenticateEmployee(employeeId, password);
        if (employee.isPresent()) {
            return "redirect:/employeeLogin";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "employeeLogin";
        }
    }
}