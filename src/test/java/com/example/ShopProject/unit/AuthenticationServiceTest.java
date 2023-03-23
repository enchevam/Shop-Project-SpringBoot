package com.example.ShopProject.unit;

import com.example.ShopProject.entities.Customer;
import com.example.ShopProject.entities.Employee;
import com.example.ShopProject.repositories.CustomerRepository;
import com.example.ShopProject.repositories.EmployeeRepository;
import com.example.ShopProject.services.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AuthenticationServiceTest {
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void testAuthenticateEmployeeWhenPasswordMatches() {
        Long employeeId = 1l;
        String password = "password";
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setPassword(new BCryptPasswordEncoder().encode(password));
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        Optional<Employee> actual = authenticationService.authenticateEmployee(employeeId, password);

        assertTrue(actual.isPresent());
        assertEquals(employeeId, actual.get().getId());
    }

    @Test
    public void testAuthenticateEmployeeWhenPasswordWrong() {
        Long employeeId = 1L;
        String password = "password";
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setPassword(new BCryptPasswordEncoder().encode("somethingElse"));
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        Optional<Employee> result = authenticationService.authenticateEmployee(employeeId, password);

        assertFalse(result.isPresent());
    }

    @Test
    public void testGetEmployeeById() {
        Long employeeId = 1L;
        Employee employee = new Employee();
        employee.setId(employeeId);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        Employee actual = authenticationService.getEmployeeById(employeeId).get();

        assertEquals(employeeId, actual.getId());
    }

    @Test
    public void testCustomerRightPassword() {
        String email = "email@email";
        String password = "password";
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(new BCryptPasswordEncoder().encode(password));
        when(customerRepository.findByEmail(email)).thenReturn(customer);

        Customer actual = authenticationService.authenticateCustomer(email, password).get();

        assertEquals(email, actual.getEmail());
    }

    @Test
    public void testCustomerWrongPassword() {
        String email = "email@email";
        String password = "password";
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(new BCryptPasswordEncoder().encode("wrongPassword"));
        when(customerRepository.findByEmail(email)).thenReturn(customer);

        Optional<Customer> actual = authenticationService.authenticateCustomer(email, password);

        assertFalse(actual.isPresent());
    }


    @Test
    public void testFindCustomerByEmail() {
        String email = "email@email";
        Customer customer = new Customer();
        customer.setEmail(email);
        when(customerRepository.findByEmail(email)).thenReturn(customer);

        Optional<Customer> actual = authenticationService.getCustomerByEmail(email);

        assertEquals(email, actual.get().getEmail());
    }
}
