package com.example.ShopProject.repositories;

import com.example.ShopProject.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Customer, Long> {


}
