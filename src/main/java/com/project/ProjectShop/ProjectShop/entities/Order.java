package com.project.ProjectShop.ProjectShop.entities;

import com.project.ProjectShop.ProjectShop.constants.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "customers_id", nullable = false)
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "employees_id" )
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @CreationTimestamp
    private Date orderDate;

    @Column(nullable = false)
    private Double totalPrice;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts;

    public Order() {
    }

    public Order(Date orderDate, Double totalPrice) {
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }


}
