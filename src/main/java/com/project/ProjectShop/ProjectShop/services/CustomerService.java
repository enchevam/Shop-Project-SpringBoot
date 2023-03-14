package com.project.ProjectShop.ProjectShop.services;

import com.project.ProjectShop.ProjectShop.entities.Customer;
import com.project.ProjectShop.ProjectShop.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class CustomerService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
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


//    public void saveCustomer(Customer customer) {
//        String encodedPassword = bCryptPasswordEncoder.encode(customer.getPassword());
//        customer.setPassword(encodedPassword);
//        System.out.println(encodedPassword);
//        customerRepository.save(customer);
//    }
//    public List<Object> isCustomerPresent(Customer customer) {
//        boolean userExists = false;
//        String message = null;
//        Customer customerGetEmail = customerRepository.getCustomerByEmail(customer.getEmail());
//        if(customerGetEmail != null){
//            userExists = true;
//            message = "User Already Exist!";
//        }
//
//        return Arrays.asList(userExists, message);
//    }
//    //@Override
//    public UserDetails loadUserByEmail(String email) throws EmailNotFoundException {
//        Customer customer = customerRepository.getCustomerByEmail(email);
//        if(customer==null){
//            throw new UsernameNotFoundException("User not found");
//        }
//        return new MyCustomerDetails(customer);
//    }
}
