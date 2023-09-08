package com.amigoes.fullstack.customer;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccess implements CustomerDao{

    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccess(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        String sql= """
               SELECT id, name,email,age
               FROM customer
                """;
        return jdbcTemplate.query(sql,customerRowMapper);

    }

    @Override
    public Optional<Customer> selectCustomerByEmail(String email) {
        String sql = """
                SELECT *
                FROM customer
                WHERE email=?
                """;
        return jdbcTemplate.query(sql,customerRowMapper,email)
                .stream()
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        String sql= """
                INSERT INTO customer(name,email,age)
                VALUES (?,?,?)
                """;

        jdbcTemplate.update(sql,
                customer.getName(),
                customer.getEmail(),
                customer.getAge()
        );
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        String sql= """
                SELECT count(email)
                FROM customer
                WHERE email=?
                """;
        Integer count= jdbcTemplate.queryForObject(sql,Integer.class,email);

        return count!=null&&count>0;
    }

    @Override
    public void deleteCustomerByEmail(String email) {
        String sql= """
                DELETE 
                FROM customer
                WHERE email=?
                """;
        int result= jdbcTemplate.update(sql,email);
        System.out.println("Deleted Customer result= "+result);
    }

    @Override
    public void updateCustomer(Customer customer) {

    }
}
