package com.amigoes.fullstack.customer;

import com.amigoes.fullstack.AbstractTestcontainersUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
// here will not connect our database with database used with developing
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestcontainersUnitTest {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
        System.out.println(applicationContext.getBeanDefinitionCount());
    }

    @Test
    void findByEmail() {
        String email = faker.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer=new Customer(
                faker.name().fullName(),
                email,
                20
        );

        customerRepository.save(customer);

        Customer actual = customerRepository.findByEmail(email);

        assertThat(actual).isNotNull();

    }

    @Test
    void existsByEmail() {
        String email = faker.internet().safeEmailAddress() + UUID.randomUUID();
        Customer customer=new Customer(
                faker.name().fullName(),
                email,
                20
        );

        customerRepository.save(customer);

        Boolean actual = customerRepository.existsByEmail(email);
        assertThat(actual).isTrue();
    }

    @Test
    void deleteByEmail() {

    }
}