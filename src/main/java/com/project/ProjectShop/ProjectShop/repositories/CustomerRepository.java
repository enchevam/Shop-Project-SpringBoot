package com.project.ProjectShop.ProjectShop.repositories;

import com.project.ProjectShop.ProjectShop.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    //public Customer getCustomerByEmail(@Param("email") String email);
    public Customer findByEmail(String email);


}
