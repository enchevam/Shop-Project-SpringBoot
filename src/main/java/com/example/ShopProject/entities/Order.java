package com.example.ShopProject.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Employee is mandatory")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @NotNull(message = "Customer is mandatory")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NotNull(message = "Date is mandatory")
    @Column(name = "date")
    private Date date;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price should be greater than 0")
    @NotNull(message = "Total price is mandatory")
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @NotBlank(message = "Status is mandatory")
    @Column(name = "status")
    private String status;

    // getters and setters
}