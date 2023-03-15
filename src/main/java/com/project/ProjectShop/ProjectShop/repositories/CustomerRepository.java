package com.project.ProjectShop.ProjectShop.repositories;

import com.project.ProjectShop.ProjectShop.entities.Customer;
import com.project.ProjectShop.ProjectShop.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    //public Customer getCustomerByEmail(@Param("email") String email);
    public Customer findByEmail(String email);

//    @Query(value = "SELECT orders.id, orders.order_date, orders.total_price " +
//            "FROM order_products " +
//            "JOIN orders ON order_products.order_id = orders.id " +
//            "JOIN customers ON orders.customers_id = customers.id " +
//            "WHERE customers.id = :customerId",
//            nativeQuery = true)
//    public List<Order> getCustomerOrders(@Param("customerId") Long customerId);


}
