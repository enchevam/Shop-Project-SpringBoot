package com.project.ProjectShop.ProjectShop.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Employee {//employee_id,first_name,last_name,age,salary
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private Double salary;
//    @OneToMany(mappedBy = "employee")
//    private List<Order> orders;


}
