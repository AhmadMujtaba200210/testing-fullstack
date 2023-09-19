package com.amigoes.fullstack.customer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CustomerJDBCDataAccess.class})
@ExtendWith(SpringExtension.class)
class CustomerJDBCDataAccessTest {
    @Autowired
    private CustomerJDBCDataAccess customerJDBCDataAccess;

    @MockBean
    private CustomerRowMapper customerRowMapper;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @Test
    void testSelectAllCustomers() throws DataAccessException {
        ArrayList<Object> objectList = new ArrayList<>();
        when(jdbcTemplate.query(Mockito.<String>any(), Mockito.<RowMapper<Object>>any())).thenReturn(objectList);
        List<Customer> actualSelectAllCustomersResult = customerJDBCDataAccess.selectAllCustomers();
        assertSame(objectList, actualSelectAllCustomersResult);
        assertTrue(actualSelectAllCustomersResult.isEmpty());
        verify(jdbcTemplate).query(Mockito.<String>any(), Mockito.<RowMapper<Object>>any());
    }


    @Test
    void testSelectCustomerByEmail() throws DataAccessException {
        when(jdbcTemplate.query(Mockito.any(), Mockito.<RowMapper<Object>>any(), isA(Object[].class)))
                .thenReturn(new ArrayList<>());
        assertFalse(customerJDBCDataAccess.selectCustomerByEmail("jane.doe@example.org").isPresent());
        verify(jdbcTemplate).query(Mockito.any(), Mockito.<RowMapper<Object>>any(), isA(Object[].class));
    }


    @Test
    void testInsertCustomer() throws DataAccessException {
        when(jdbcTemplate.update(Mockito.any(), isA(Object[].class))).thenReturn(1);

        Customer customer = new Customer();
        customer.setAge(1);
        customer.setEmail("jane.doe@example.org");
        customer.setGender(Gender.MALE);
        customer.setId(1L);
        customer.setName("Name");
        customerJDBCDataAccess.insertCustomer(customer);
        verify(jdbcTemplate).update(Mockito.any(), isA(Object[].class));
    }


    @Test
    void testExistsPersonWithEmail() throws DataAccessException {
        when(jdbcTemplate.queryForObject(Mockito.any(), Mockito.<Class<Integer>>any(), isA(Object[].class)))
                .thenReturn(1);
        assertTrue(customerJDBCDataAccess.existsPersonWithEmail("jane.doe@example.org"));
        verify(jdbcTemplate).queryForObject(Mockito.any(), Mockito.<Class<Integer>>any(), isA(Object[].class));
    }


    @Test
    void testExistsPersonWithEmail2() throws DataAccessException {
        when(jdbcTemplate.queryForObject(Mockito.any(), Mockito.<Class<Integer>>any(), isA(Object[].class)))
                .thenReturn(0);
        assertFalse(customerJDBCDataAccess.existsPersonWithEmail("jane.doe@example.org"));
        verify(jdbcTemplate).queryForObject(Mockito.any(), Mockito.<Class<Integer>>any(), isA(Object[].class));
    }


    @Test
    void testExistsPersonWithEmail3() throws DataAccessException {
        when(jdbcTemplate.queryForObject(Mockito.any(), Mockito.<Class<Integer>>any(), isA(Object[].class)))
                .thenReturn(null);
        assertFalse(customerJDBCDataAccess.existsPersonWithEmail("jane.doe@example.org"));
        verify(jdbcTemplate).queryForObject(Mockito.any(), Mockito.<Class<Integer>>any(), isA(Object[].class));
    }


    @Test
    void testDeleteCustomerByEmail() throws DataAccessException {
        when(jdbcTemplate.update(Mockito.any(), isA(Object[].class))).thenReturn(1);
        customerJDBCDataAccess.deleteCustomerByEmail("jane.doe@example.org");
        verify(jdbcTemplate).update(Mockito.any(), isA(Object[].class));
    }


    @Test
    void testUpdateCustomer() {

    }
}