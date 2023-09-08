package com.amigoes.fullstack.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {


    Customer findByEmail(String email);
    Boolean existsByEmail(String email);
    void deleteByEmail(String email);
}
