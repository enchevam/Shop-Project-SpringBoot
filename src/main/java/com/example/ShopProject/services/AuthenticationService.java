package com.example.ShopProject.services;

import com.example.ShopProject.entities.Employee;
import com.example.ShopProject.repositories.EmployeeRepository;
import com.example.ShopProject.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private EmployeeRepository employeeRepository;


    public Optional<Employee> authenticateEmployee(Long employeeId, String password){
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        if (employee != null) {
            return Optional.of(employee);
        }
        return Optional.empty();
    }
    //&& BCrypt.checkpw(password, employee.getPassword())

    public Optional<Employee> getEmployeeById(Long employeeId){
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        if (employee != null) {
            return Optional.of(employee);
        }
        return Optional.empty();
    }
}