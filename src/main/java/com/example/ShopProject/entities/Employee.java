package com.example.ShopProject.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "employees")
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is mandatory")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Column(name = "last_name")
    private String lastName;

    @Min(value = 18, message = "Age should be greater than or equal to 18")
    @NotNull(message = "Age is mandatory")
    @Column(name = "age")
    private Integer age;

    @DecimalMin(value = "0.0", inclusive = false, message = "Salary should be greater than 0")
    @NotNull(message = "Salary is mandatory")
    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "password")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    // getters and setters
}
