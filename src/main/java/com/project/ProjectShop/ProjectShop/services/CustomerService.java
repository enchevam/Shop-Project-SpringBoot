package com.project.ProjectShop.ProjectShop.services;

import com.project.ProjectShop.ProjectShop.entities.Customer;
import com.project.ProjectShop.ProjectShop.entities.Order;
import com.project.ProjectShop.ProjectShop.repositories.CustomerRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Service
public class CustomerService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private CustomerRepository customerRepository;

    public Customer findById(Long customerId) {
        return customerRepository.findById(customerId).get();
    }

    public void updateCustomerInformation(Customer customer) {
        customerRepository.save(customer);
    }

    public void register(Customer customer) {

        // Проверка за съществуващ потребител с този имейл
        Customer existingCustomer = customerRepository.findByEmail(customer.getEmail());
        if (existingCustomer != null) {
            throw new RuntimeException("Email already exists");
        }

        // Хеширане на паролата
        String hashedPassword = BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt());
        customer.setPassword(hashedPassword);

        // Записване на клиента в базата
        customerRepository.save(customer);
    }

}
