package com.example.ShopProject.repositories;

import com.example.ShopProject.entities.Employee;
import com.example.ShopProject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


}
