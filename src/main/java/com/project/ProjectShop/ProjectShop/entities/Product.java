package com.project.ProjectShop.ProjectShop.entities;

import com.project.ProjectShop.ProjectShop.constants.Type;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product { //Данни за стоките: product_id,name,price,quantity,type,color,expires_in
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotNull
    @Size(min=2, max=50)
    @Column(length = 50, nullable = false)
    private String name;
    @NotNull
    @Size(min=2, max=50)
    @Column(length = 50, nullable = false)
    private String color;
    @NotNull
    @Size(min=2, max=50)
    @Column(length = 50, nullable = false)
    private String expireDate;
    @NotNull
    @Min(0)
    @Max(500)
    @Column(nullable = false)
    private Double price;
    @NotNull
    @Min(0)
    @Max(500)
    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private Type productType;

    private String imageUrl;





}
