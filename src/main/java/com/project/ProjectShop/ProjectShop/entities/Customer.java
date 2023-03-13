package com.project.ProjectShop.ProjectShop.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 2, max = 50, message = "Size must be minimum 2 ")
    @Column(nullable = false)
    private String firstName;

    @Length(min = 2, message = "Size must be minimum 2 ")
    @Column(nullable = false)
    private String lastName;
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Length(min = 2, message = "Size must be minimum 2 ")
    @Column(nullable = false)
    private String address;

    @Length(min = 2, message = "Size must be minimum 2 ")
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

}
