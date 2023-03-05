package com.project.ProjectShop.ProjectShop.repositories;

import com.project.ProjectShop.ProjectShop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
