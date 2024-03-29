package com.example.ShopProject.entities;

import com.example.ShopProject.utils.ProductType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Product name cannot be blank")
    private String name;

    @Column(name = "price")
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.00", inclusive = false, message = "Price must be greater than 0.00")
    private BigDecimal price;

    @Column(name = "quantity")
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity must be greater than or equal to 1")
    private Integer quantity;

    @Column(name = "type")
    @NotNull(message = "Type cannot be blank")
    @Enumerated(EnumType.STRING)
    private ProductType type;

    @Column(name = "color")
    @NotBlank(message = "Color cannot be blank")
    private String color;

    @Column(name = "expire_in")
    @NotNull(message = "Expire in cannot be null")
    @Future(message = "Expire date must be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireDate;
    @Column
    private String imageUrl;

    @Column
    @NotNull
    private boolean available;

    public Product() {
    }

    public Product(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }
    public Product(Long id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }

}