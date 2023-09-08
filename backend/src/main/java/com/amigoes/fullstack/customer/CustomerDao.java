package com.amigoes.fullstack.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> selectAllCustomers();
    Optional<Customer> selectCustomerByEmail(String email);
    void insertCustomer(Customer customer);
    boolean existsPersonWithEmail(String email);
    void deleteCustomerByEmail(String email);
    void updateCustomer(Customer customer);
}
