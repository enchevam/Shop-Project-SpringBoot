//package com.project.ProjectShop.ProjectShop.entities;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Getter
//@Setter
//@Entity
//public class OrderItems {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @ManyToOne
//    @JoinColumn(name = "order_id", nullable = false)
//    private Order order;
//    @ManyToOne
//    @JoinColumn(name = "product_id", nullable = false)
//    private Product product;
//    //@Column(nullable = false)
//    private Integer quantity;
//
//    //@Column(nullable = false)
//    private Double productPrice;
//
//
//}
