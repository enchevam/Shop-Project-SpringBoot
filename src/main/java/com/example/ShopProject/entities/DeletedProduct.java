package com.example.ShopProject.entities;

import com.example.ShopProject.utils.ProductType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "deleted_products")
@Getter
@Setter
public class DeletedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ProductType type;

    @Column(name = "color")
    private String color;

    @Column(name = "expire_in")
    private LocalDate expireIn;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public DeletedProduct(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.type = product.getType();
        this.color = product.getColor();
        this.expireIn = product.getExpireIn();
        this.deletedAt = new Timestamp(System.currentTimeMillis()).toLocalDateTime();
    }
}