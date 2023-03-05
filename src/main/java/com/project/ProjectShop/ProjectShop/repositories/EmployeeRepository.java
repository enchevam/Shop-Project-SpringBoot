package com.project.ProjectShop.ProjectShop.repositories;

import com.project.ProjectShop.ProjectShop.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
