package com.example.ShopProject.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "order_products")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Order is mandatory")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @NotNull(message = "Product is mandatory")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price should be greater than 0")
    @NotNull(message = "Price is mandatory")
    @Column(name = "price")
    private BigDecimal totalPrice;

    @Min(value = 1, message = "Quantity should be greater than 0")
    @NotNull(message = "Quantity is mandatory")
    @Column(name = "quantity")
    private Integer quantity;

}