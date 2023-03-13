package com.project.ProjectShop.ProjectShop.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name = "order_products")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn( nullable = false)
    private Order order;

    @ManyToOne //(fetch = FetchType.LAZY)
    @JoinColumn( nullable = false)
    private Product product;

    private Integer quantity;

    private Double totalPrice;


}