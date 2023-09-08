package com.amigoes.fullstack.customer;

import com.amigoes.fullstack.exception.RequestValidationException;
import com.amigoes.fullstack.exception.ResourceDuplicateFoundException;
import com.amigoes.fullstack.exception.ResourceNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServicesTest {
    @Mock
    private CustomerDao customerDao;
    private CustomerServices underTest;

    @BeforeEach
    void setUp() {
        underTest=new CustomerServices(customerDao);
    }

    @Test
    void registerCustomer() {
        //GIVEN
        String email="alex@gmail.com";

        //WHEN
        when(customerDao.existsPersonWithEmail(email)).thenReturn(false);
        RegisterRequest registerRequest=new RegisterRequest(
                "Alex",
                email,
                19
        );
        underTest.registerCustomer(registerRequest);

        //THEN
        ArgumentCaptor<Customer> customerArgumentCaptor=ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).insertCustomer(customerArgumentCaptor.capture());

        Customer capturedArgument = customerArgumentCaptor.getValue();

        assertThat(capturedArgument.getName()).isEqualTo(registerRequest.name());
        assertThat(capturedArgument.getEmail()).isEqualTo(registerRequest.email());
        assertThat(capturedArgument.getAge()).isEqualTo(registerRequest.age());
    }
    @Test
    void willThrowWhenEmailExistsWhileRegisteringCustomer() {
        //GIVEN
        String email="alex@gmail.com";

        when(customerDao.existsPersonWithEmail(email)).thenReturn(true);

        RegisterRequest registerRequest=new RegisterRequest(
                "Alex",
                email,
                19
        );
        //WHEN
        assertThatThrownBy(()->underTest.registerCustomer(registerRequest))
                .isInstanceOf(ResourceDuplicateFoundException.class)
                        .hasMessage("Email Already Taken!");
        //THEN
        verify(customerDao,never()).insertCustomer(any());
    }

    @Test
    void getCustomerByEmail() {
        //GIVEN
        long id=10;
        String email="alex@example.com";
        Customer customer=new Customer(
                id,"Alex",email,20
        );

        when(customerDao.selectCustomerByEmail(email)).thenReturn(Optional.of(customer));

        //WHEN
        Customer actual=underTest.getCustomerByEmail(email);

        //HOW


    }


    @Test
    void willThrowExceptionWhenGetCustomerByEmailReturnsEmptyOptional() {
        //GIVEN

        String email="example@gmail.com";
        when(customerDao.selectCustomerByEmail(email)).thenReturn(Optional.empty());

        //WHEN

        //HOW
        assertThatThrownBy(()->underTest.getCustomerByEmail(email))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Customer not found!");

    }


    @Test
    void getCustomerList() {
        //WHEN
        underTest.getCustomerList();

        //THEN
        verify(customerDao).selectAllCustomers();
    }

    @Test
    void updateCustomerWithAllProperties() {
        //GIVEN
        String email="example1@example.com";
        Customer customer=new Customer(
                "Alex",email,19
        );
        when(customerDao.selectCustomerByEmail(email)).thenReturn(Optional.of(customer));
        String newEmail = "example1@gmail.com";
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                "ahmad", newEmail, 20
        );

        when(customerDao.existsPersonWithEmail(newEmail)).thenReturn(false);
        //WHEN
        underTest.updateCustomer(updateRequest,email);

        //THEN
        ArgumentCaptor<Customer> argumentCaptor=ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(argumentCaptor.capture());
        Customer capturedCustomer = argumentCaptor.getValue();
        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(updateRequest.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());

    }

    @Test
    void canUpdateOnlyCustomerName() {
        //GIVEN
        String email="example1@example.com";
        Customer customer=new Customer(
                "Alex",email,19
        );
        when(customerDao.selectCustomerByEmail(email)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                "ahmad", null, null
        );

        //WHEN
        underTest.updateCustomer(updateRequest,email);

        //THEN
        ArgumentCaptor<Customer> argumentCaptor=ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(argumentCaptor.capture());
        Customer capturedCustomer = argumentCaptor.getValue();
        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());

    }

    @Test
    void canUpdateOnlyCustomerEmail() {
        //GIVEN
        String email="example1@example.com";
        Customer customer=new Customer(
                "Alex",email,19
        );
        when(customerDao.selectCustomerByEmail(email)).thenReturn(Optional.of(customer));
        String newEmail="ahmad@example.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                null, newEmail, null
        );
        when(customerDao.existsPersonWithEmail(newEmail)).thenReturn(false);
        //WHEN
        underTest.updateCustomer(updateRequest,email);

        //THEN
        ArgumentCaptor<Customer> argumentCaptor=ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(argumentCaptor.capture());
        Customer capturedCustomer = argumentCaptor.getValue();
        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(updateRequest.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());

    }
    @Test
    void willThrowWhenTryingToUpdateCustomerEmailWhenAlreadyTaken() {
        //GIVEN
        String email="example1@example.com";
        Customer customer=new Customer(
                "Alex",email,19
        );
        when(customerDao.selectCustomerByEmail(email)).thenReturn(Optional.of(customer));
        String newEmail="ahmad@example.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                null, newEmail, null
        );

        when(customerDao.existsPersonWithEmail(newEmail)).thenReturn(true);
        //WHEN
            assertThatThrownBy(()->underTest.updateCustomer(updateRequest,email))
                    .isInstanceOf(ResourceDuplicateFoundException.class)
                    .hasMessage("email already taken!");
        //THEN

        verify(customerDao,never()).updateCustomer(any());
    }

    @Test
    void canUpdateOnlyCustomerAge() {
        //GIVEN
        String email="example1@example.com";
        Customer customer=new Customer(
                "Alex",email,19
        );
        when(customerDao.selectCustomerByEmail(email)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                null, null, 22
        );

        //WHEN
        underTest.updateCustomer(updateRequest,email);

        //THEN
        ArgumentCaptor<Customer> argumentCaptor=ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(argumentCaptor.capture());
        Customer capturedCustomer = argumentCaptor.getValue();
        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());

    }

    @Test
    void willThrowWhenUpdateHasNoChanges() {
        //GIVEN
        String email="example1@example.com";
        Customer customer=new Customer(
                "Alex",email,19
        );
        when(customerDao.selectCustomerByEmail(email)).thenReturn(Optional.of(customer));
        String newEmail = "example1@gmail.com";
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                customer.getName(), customer.getEmail(), customer.getAge()
        );

        //WHEN
        assertThatThrownBy(()->underTest.updateCustomer(updateRequest,email))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("no data changes found");
        //THEN
        verify(customerDao,never()).updateCustomer(any());

    }

    @Test
    void deleteCustomer() {
        //GIVEN
        String email="example@example.com";
        when(customerDao.existsPersonWithEmail(email)).thenReturn(true);

        //WHEN
        underTest.deleteCustomer(email);

        //THEN
        verify(customerDao).deleteCustomerByEmail(email);
    }

    @Test
    void willThrowExceptionWhenCustomerNotFound() {
        //GIVEN
        String email="example@example.com";
        when(customerDao.existsPersonWithEmail(email)).thenReturn(false);

        //WHEN
        assertThatThrownBy(()->underTest.deleteCustomer(email))
                .isInstanceOf(ResourceNotFoundException.class)
                        .hasMessage("Email Not Exists!");

        //THEN
        verify(customerDao,never()).deleteCustomerByEmail(email);
    }


}

