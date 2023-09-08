package com.amigoes.fullstack.customer;


import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class CustomerJPADataAccess implements CustomerDao{
    private final CustomerRepository repository;


    public CustomerJPADataAccess(CustomerRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Customer> selectAllCustomers() {
        return repository.findAll();
    }

    @Override
    public Optional<Customer> selectCustomerByEmail(String email) {
        return Optional.ofNullable(repository.findByEmail(email));
    }

    @Override
    public void insertCustomer(Customer customer) {
            repository.save(customer);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public void deleteCustomerByEmail(String email) {
        repository.deleteByEmail(email);
    }

    @Override
    public void updateCustomer(Customer customer) {
        repository.save(customer);
    }
}
