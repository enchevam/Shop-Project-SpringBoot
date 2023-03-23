package com.example.ShopProject.authorization;


import com.example.ShopProject.entities.Employee;
import com.example.ShopProject.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String employeeId) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findById(Long.valueOf(employeeId)).get();
        System.out.println("autheticated user from userDetailsSeeciseImplement");

        if (employee == null) {
            throw new UsernameNotFoundException("Could not find employee");
        }
        User.builder()
                .username(employee.getId().toString())
                .password(employee.getPassword())
                .roles("EMPLOYEE")
                .build();
        System.out.println("logged employee" + employee);
        return new MyUserDetails(employee);
    }
}
