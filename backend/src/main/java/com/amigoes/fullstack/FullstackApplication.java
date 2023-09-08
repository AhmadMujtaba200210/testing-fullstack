package com.amigoes.fullstack;

import com.amigoes.fullstack.customer.Customer;
import com.amigoes.fullstack.customer.CustomerRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.Random;

@SpringBootApplication
public class FullstackApplication {

    public static void main(String[] args) {
        SpringApplication.run(FullstackApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository){
        return args -> {
            var faker=new Faker().name();
            Random random=new Random();
            Customer customer=new Customer(
                    faker.firstName()+" "+faker.lastName(),
                    faker.firstName().toLowerCase()+"."+faker.lastName()+"@example.com",
                    random.nextInt(16,70)
            );
            customerRepository.save(customer);
        };

    }

}
