package com.amigoes.fullstack.journey;

import com.amigoes.fullstack.customer.Customer;
import com.amigoes.fullstack.customer.RegisterRequest;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerIT {
    @Autowired
    private WebTestClient webTestClient;
    private static final Random random = new Random();
    // never invoke any method directly in integration testing ( like autowired )

    @Test
    void canRegisterCustomer() {
        // create a registration requests
        Faker faker = new Faker();
        Name fakerName = faker.name();
        String name = fakerName.fullName();
        String email = fakerName.lastName() + UUID.randomUUID() + "@example.com";
        int age = random.nextInt(1, 100);
        RegisterRequest registerRequest = new RegisterRequest(
                name, email, age
        );
        // send a post request
        webTestClient.post()
                .uri("/api/v1/customer/register")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(registerRequest), RegisterRequest.class)
                .exchange()
                .expectStatus()
                .isOk();
        // get all customer
        List<Customer> allCustomers = webTestClient.get()
                .uri("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

        // check if customer is present
        Customer expectedCustomer = new Customer(
                name, email, age
        );
        assertThat(allCustomers)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expectedCustomer);
        // get customer by id
//        var id=allCustomers.stream()
//                        .filter(customer -> customer.getEmail().equals(email))
//                                .map(Customer::getId)
//                                        .findFirst()
//                                                .orElseThrow();
//        expectedCustomer.setId(id);
//        Customer customer = webTestClient.get()
//                .uri("/api/v1/customer/user/{id}",id)
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .expectStatus()
//                .isOk()
//                .expectBody(new ParameterizedTypeReference<Customer>() {
//                })
//                .returnResult()
//                .getResponseBody();

        // get Customer by email
        webTestClient.get()
                .uri("/api/v1/customer/user/{email}", email)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();
    }

    @Test
    void canDeleteCustomer() {
        // create a registration requests
        Faker faker = new Faker();
        Name fakerName = faker.name();
        String name = fakerName.fullName();
        String email = fakerName.lastName() + UUID.randomUUID() + "@example.com";
        int age = random.nextInt(1, 100);
        RegisterRequest registerRequest = new RegisterRequest(
                name, email, age
        );
        // send a post request
        webTestClient.post()
                .uri("/api/v1/customer/register")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(registerRequest), RegisterRequest.class)
                .exchange()
                .expectStatus()
                .isOk();
        // get all customer
        List<Customer> allCustomers = webTestClient.get()
                .uri("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

        // get customer by id
//        var id=allCustomers.stream()
//                        .filter(customer -> customer.getEmail().equals(email))
//                                .map(Customer::getId)
//                                        .findFirst()
//                                                .orElseThrow();
//        expectedCustomer.setId(id);
//        Customer customer = webTestClient.get()
//                .uri("/api/v1/customer/user/{id}",id)
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .expectStatus()
//                .isOk()
//                .expectBody(new ParameterizedTypeReference<Customer>() {
//                })
//                .returnResult()
//                .getResponseBody();


        //delete customer by email
        webTestClient.delete()
                .uri("/api/v1/customer/delete/{email}", email)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();


        // get Customer by email
        webTestClient.get()
                .uri("/api/v1/customer/user/{email}", email)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

//    @Test
//    void canUpdateCustomer() {
//        // create a registration requests
//        Faker faker = new Faker();
//        Name fakerName = faker.name();
//        String name = fakerName.fullName();
//        String email = fakerName.lastName() + UUID.randomUUID() + "@example.com";
//        int age = random.nextInt(1, 100);
//        RegisterRequest registerRequest = new RegisterRequest(
//                name, email, age
//        );
//        // send a post request
//        webTestClient.post()
//                .uri("/api/v1/customer/register")
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(Mono.just(registerRequest), RegisterRequest.class)
//                .exchange()
//                .expectStatus()
//                .isOk();
//        // get all customer
//        List<Customer> allCustomers = webTestClient.get()
//                .uri("/api/v1/customer")
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .expectStatus()
//                .isOk()
//                .expectBodyList(new ParameterizedTypeReference<Customer>() {
//                })
//                .returnResult()
//                .getResponseBody();
//
//        // get customer by id
//        var id=allCustomers.stream()
//                        .filter(customer -> customer.getEmail().equals(email))
//                                .map(Customer::getId)
//                                        .findFirst()
//                                                .orElseThrow();
////        expectedCustomer.setId(id);
////        Customer customer = webTestClient.get()
////                .uri("/api/v1/customer/user/{id}",id)
////                .accept(MediaType.APPLICATION_JSON)
////                .exchange()
////                .expectStatus()
////                .isOk()
////                .expectBody(new ParameterizedTypeReference<Customer>() {
////                })
////                .returnResult()
////                .getResponseBody();
//
//        String newName = "newName";
//        CustomerUpdateRequest updateRequest=new CustomerUpdateRequest(
//                newName,null,null
//        );
//
//        //update customer by email
//        webTestClient.put()
//                .uri("/api/v1/customer/update/{email}", email)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(Mono.just(updateRequest),CustomerUpdateRequest.class)
//                .exchange()
//                .expectStatus()
//                .isOk();
//
//
//        // get Customer by email
//        Customer updatedCustomer = webTestClient.get()
//                .uri("/api/v1/customer/user/{email}", email)
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .expectStatus()
//                .isOk()
//                .expectBody(Customer.class)
//                .returnResult()
//                .getResponseBody();
//
//        Customer expected = new Customer(
//                id,newName,email,age
//        );
//
//        assertThat(updatedCustomer).isEqualTo(expected);
//    }
}
