package com.example.ShopProject.unit;

import com.example.ShopProject.entities.Customer;
import com.example.ShopProject.repositories.CustomerRepository;
import com.example.ShopProject.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void testCustomerRegisterIfSuccess() {
        String email = "email@email";
        String pass = "123456";
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(pass);

        when(customerRepository.findByEmail(email)).thenReturn(null);
        customerService.register(customer);

        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testCustomerRegisterIfExist() {
        String email = "email@email";
        String pass = "123456";
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(pass);
        when(customerRepository.findByEmail(email)).thenReturn(customer);

        assertThrows(RuntimeException.class, () -> customerService.register(customer));
    }

    @Test
    public void testFindById() {
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Customer actual = customerService.findById(customerId);

        assertEquals(customer, actual);
    }
    @Test
    public void testUpdateCustomerInformation() {
        Customer customer = new Customer();
        customer.setId(1L);

        when(customerRepository.save(customer)).thenReturn(customer);

        customerService.updateCustomerInformation(customer);

        verify(customerRepository, times(1)).save(customer);
    }

}
