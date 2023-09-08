package com.amigoes.fullstack.customer;

import com.amigoes.fullstack.AbstractTestcontainersUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerJDBCDataAccessTest extends AbstractTestcontainersUnitTest {

    private final CustomerRowMapper customerRowMapper=new CustomerRowMapper();
    private CustomerJDBCDataAccess underTest;

    @BeforeEach
    void setUp() {
       underTest=new CustomerJDBCDataAccess(
         getJdbcTemplate(),
         customerRowMapper
       );
    }

    @Test
    void selectAllCustomers() {
        Customer customer=new Customer(
                faker.name().fullName(),
                faker.internet().safeEmailAddress()+ UUID.randomUUID(),
                20
        );

        underTest.insertCustomer(customer);

        List<Customer> customers=underTest.selectAllCustomers();

        assertThat(customers).isNotEmpty();
    }

    @Test
    void selectCustomerByEmail() {
        String email = faker.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer=new Customer(
                faker.name().fullName(),
                email,
                20
        );
        underTest.insertCustomer(customer);

//        String emailU=underTest.selectCustomerByEmail(email)
//                .stream()
//                .map(Customer::getEmail)
//                .filter(cEmail -> cEmail.equals(email))
//                .findFirst()
//                .orElseThrow();

        Optional<Customer> actual = underTest.selectCustomerByEmail(email);
        assertThat(actual).isPresent();
    }


    @Test
    void existsPersonWithEmail() {
        String email = faker.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer=new Customer(
                faker.name().fullName(),
                email,
                20
        );
        underTest.insertCustomer(customer);
        boolean actual = underTest.existsPersonWithEmail(email);
        assertThat(actual).isTrue();
    }

    @Test
    void deleteCustomerByEmail() {
        String email = faker.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer=new Customer(
                faker.name().fullName(),
                email,
                20
        );
        underTest.insertCustomer(customer);
        underTest.deleteCustomerByEmail(email);
        boolean actual = underTest.existsPersonWithEmail(email);
        assertThat(actual).isFalse();
    }

    @Test
    void updateCustomer() {


    }
}