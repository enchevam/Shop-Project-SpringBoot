package com.example.ShopProject.entities;

import com.example.ShopProject.utils.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @NotNull(message = "Employee is mandatory")
    private Employee employee;

    @NotNull(message = "Customer is mandatory")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "date")
    @NotNull(message = "Date is mandatory")
    @Temporal(TemporalType.TIMESTAMP)
    @OrderBy("date DESC")
    private Date orderDate;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price should be greater than 0")
    @NotNull(message = "Total price is mandatory")
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "status")
    @NotNull(message = "Type cannot be blank")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts;


    public void setStatus(OrderStatus status) {
        this.orderStatus = status;
    }

}