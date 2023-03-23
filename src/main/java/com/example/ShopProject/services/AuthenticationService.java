package com.example.ShopProject.services;

import com.example.ShopProject.entities.Customer;
import com.example.ShopProject.entities.Employee;
import com.example.ShopProject.repositories.CustomerRepository;
import com.example.ShopProject.repositories.EmployeeRepository;
import com.example.ShopProject.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public Optional<Employee> authenticateEmployee(Long employeeId, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            if (encoder.matches(password, employee.getPassword())) {
                return Optional.of(employee);
            }
        }
        return Optional.empty();
    }

    public Optional<Employee> getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        if (employee != null) {
            return Optional.of(employee);
        }
        return Optional.empty();
    }

    public Optional<Customer> authenticateCustomer(String email, String password) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }
        if (new BCryptPasswordEncoder().matches(password, customer.getPassword())) {
            return Optional.of(customer);
        }
        return Optional.empty();
    }


    public Optional<Customer> getCustomerByEmail(String email) {
        return Optional.ofNullable(customerRepository.findByEmail(email));
    }

    public Employee takeCustomerSession(HttpSession session) {
        String id = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                id = ((UserDetails) principal).getUsername();
            }
        }
        Employee employee = employeeRepository.findById(Long.valueOf(id)).get();
        session.setAttribute("employee", employee);
        if (employee == null) {
            throw new RuntimeException("No such customer");
        }
        return employee;
    }
}