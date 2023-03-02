package com.example.ShopProject.services;

import com.example.ShopProject.entities.Employee;
import com.example.ShopProject.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private EmployeeRepository employeeRepository;


    public Optional<Employee> authenticateEmployee(Long employeeId, String password) throws ChangeSetPersister.NotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        if (employee != null && employee.getPassword().equals(password)) {
            return Optional.of(employee);
        }
        return Optional.empty();
    }
}