package com.project.ProjectShop.ProjectShop.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {//employee_id,first_name,last_name,age,salary
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String firstName;

    @Column(nullable = false, unique = true)
    private String lastName;

    @Column(nullable = false, unique = true)
    private Integer age;

    @Column(nullable = false, unique = true)
    private Double salary;



}
