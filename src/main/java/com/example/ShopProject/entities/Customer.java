package com.example.ShopProject.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Column(name = "email")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @Length(min = 2, message = "Size must be minimum 2 ")
    @Column(nullable = false)
    private String address;

    @Column(name = "password")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}