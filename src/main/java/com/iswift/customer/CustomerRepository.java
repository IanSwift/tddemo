package com.iswift.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
    Customer save(Customer customer);

    List<Customer> findByFirstNameAndLastName(String firstName, String lastName);
}
