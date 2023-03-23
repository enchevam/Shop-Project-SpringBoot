package com.example.ShopProject.unit;

import com.example.ShopProject.entities.Employee;
import com.example.ShopProject.repositories.EmployeeRepository;
import com.example.ShopProject.services.EmployeeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeService employeeService;



    @Test
    public void testGetSortedEmployeesByFirstNAme() {
        Employee employee1 = new Employee("firsName1", "lastName1", BigDecimal.valueOf(1000));
        Employee employee2 = new Employee("firsName2", "lastName2", BigDecimal.valueOf(3000));
        Employee employee3 = new Employee("firsName3", "lastName3", BigDecimal.valueOf(2000));
        List<Employee> employees = Arrays.asList(employee1, employee2, employee3);

        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> actual = employeeService.getSortedEmployees("firstName", "asc");
        assertEquals(employee1, actual.get(0));
        assertEquals(employee2, actual.get(1));
        assertEquals(employee3, actual.get(2));

    }
    @Test
    public void testGetSortedEmployeesByLastName() {
        Employee employee1 = new Employee("firsName1", "lastName1", BigDecimal.valueOf(1000));
        Employee employee2 = new Employee("firsName2", "lastName2", BigDecimal.valueOf(3000));
        Employee employee3 = new Employee("firsName3", "lastName3", BigDecimal.valueOf(2000));

        List<Employee> employees = Arrays.asList(employee1, employee2, employee3);

        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> actual = employeeService.getSortedEmployees("lastName", "desc");

        assertEquals(employee3, actual.get(0));
        assertEquals(employee2, actual.get(1));
        assertEquals(employee1, actual.get(2));

    }
    @Test
    public void testGetSortedEmployeesBySalary() {
        Employee employee1 = new Employee("firsName1", "lastName1", BigDecimal.valueOf(1000));
        Employee employee2 = new Employee("firsName2", "lastName2", BigDecimal.valueOf(3000));
        Employee employee3 = new Employee("firsName3", "lastName3", BigDecimal.valueOf(2000));
        List<Employee> employees = Arrays.asList(employee1, employee2, employee3);

        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> actual = employeeService.getSortedEmployees("salary", "asc");
        assertEquals(BigDecimal.valueOf(1000), actual.get(0).getSalary());
        assertEquals(BigDecimal.valueOf(2000), actual.get(1).getSalary());
        assertEquals(BigDecimal.valueOf(3000), actual.get(2).getSalary());
    }
}