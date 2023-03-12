package com.example.ShopProject.controllers;

import com.example.ShopProject.entities.Employee;
import com.example.ShopProject.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/shop")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public String getEmployees(@RequestParam(defaultValue = "name") String sortBy,
                               @RequestParam(defaultValue = "asc") String sortDirection,
                               Model model) {
        List<Employee> employees = employeeService.getSortedEmployees(sortBy, sortDirection);

        model.addAttribute("employees", employees);
        model.addAttribute("sortDirection", sortDirection);

        return "shop/employees";
    }
}
