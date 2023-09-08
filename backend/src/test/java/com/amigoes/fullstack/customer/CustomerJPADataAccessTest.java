package com.amigoes.fullstack.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class CustomerJPADataAccessTest {
    @Mock
    private CustomerRepository customerRepository;
    private CustomerJPADataAccess underTest;
    private AutoCloseable autoCloseable;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest=new CustomerJPADataAccess(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
        underTest.selectAllCustomers();
        verify(customerRepository).findAll();
    }

    @Test
    void selectCustomerByEmail() {
        underTest.selectCustomerByEmail("ahmad@example.com");
        verify(customerRepository).findByEmail("ahmad@example.com");
    }

    @Test
    void insertCustomer() {
        Customer customer=new Customer(
                1L,
                "Ali",
                "ali.mehar@example.com",
                20
        );
        underTest.insertCustomer(customer);
        verify(customerRepository).save(customer);
    }

    @Test
    void existsPersonWithEmail() {
        underTest.existsPersonWithEmail("ali.mehar@example.com");
        verify(customerRepository).existsByEmail("ali.mehar@example.com");

    }

    @Test
    void deleteCustomerByEmail() {
        underTest.deleteCustomerByEmail("ali.mehar@example.com");
        verify(customerRepository).deleteByEmail("ali.mehar@example.com");
    }

    @Test
    void updateCustomer() {
        Customer customer=new Customer(
                3L,
                "Ahmad Mujtaba",
                "ahmad.mujtaba@example.com",
                23
        );
        underTest.updateCustomer(customer);
        verify(customerRepository).save(customer);
    }
}