package com.project.ProjectShop.ProjectShop.entities;

import com.project.ProjectShop.ProjectShop.constants.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(nullable = false)
    private String fullName;
    @Email
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String address;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @CreationTimestamp
    private Date orderDate;
    @Column(nullable = false)
    private Double totalPrice;
    @OneToMany(mappedBy = "order")
    private List<OrderItems> orderDetailsList;


}
