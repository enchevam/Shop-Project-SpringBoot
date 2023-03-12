package com.example.ShopProject.services;

import com.example.ShopProject.entities.Employee;
import com.example.ShopProject.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getSortedEmployees(String sortBy, String sortDirection) {
        List<Employee> employees = employeeRepository.findAll();

        Comparator<Employee> comparator = null;

        switch (sortBy) {
            case "firstName":
                comparator = Comparator.comparing(Employee::getFirstName);
                break;
            case "lastName":
                comparator = Comparator.comparing(Employee::getLastName);
                break;
            case "salary":
                comparator = Comparator.comparing(Employee::getSalary);
                break;
        }

        if (comparator != null) {
            if ("desc".equals(sortDirection)) {
                comparator = comparator.reversed();
            }
            employees.sort(comparator);
        }
        return employees;
    }
}
