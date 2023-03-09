package com.example.ShopProject.services;

import com.example.ShopProject.entities.Customer;
import com.example.ShopProject.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

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
