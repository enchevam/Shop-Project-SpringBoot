package com.project.ProjectShop.ProjectShop.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    //@Column( nullable = false)
    private Order order;

    @ManyToOne //(fetch = FetchType.LAZY)
    //@Column( nullable = false)
    private Product product;

    private Integer quantity;

    private Double totalPrice;


}