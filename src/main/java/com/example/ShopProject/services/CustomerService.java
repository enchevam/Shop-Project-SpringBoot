package com.example.ShopProject.services;

import com.example.ShopProject.entities.Customer;
import com.example.ShopProject.entities.Order;
import com.example.ShopProject.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public void register(Customer customer) {

        Customer existingCustomer = customerRepository.findByEmail(customer.getEmail());
        if (existingCustomer != null) {
            throw new RuntimeException("Email already exists");
        }

        String hashedPassword = BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt());
        customer.setPassword(hashedPassword);

        customerRepository.save(customer);
    }

    public void updateCustomerInformation(Customer customer) {
        customerRepository.save(customer);
    }

    public Customer findById(Long customerId) {
        return customerRepository.findById(customerId).get();
    }

    public Customer findByEmail(String email) {
        Optional<Customer> optionalCustomer = Optional.ofNullable(customerRepository.findByEmail(email));
        if (optionalCustomer.isPresent()) {
            return optionalCustomer.get();
        } else {
            // Тук можеш да хвърлиш изключение или да върнеш null в зависимост от желанието си
            throw new RuntimeException("Customer not found");
        }
    }
}
