package com.example.ShopProject.configuration;

import com.example.ShopProject.entities.OrderProduct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyConfig {
    @Bean
    @Primary
    public List<OrderProduct> orderProducts() {
        return new ArrayList<>();
    }
}
