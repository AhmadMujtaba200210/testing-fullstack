package com.amigoes.fullstack.customer;

import com.amigoes.fullstack.exception.RequestValidationException;
import com.amigoes.fullstack.exception.ResourceDuplicateFoundException;
import com.amigoes.fullstack.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServices {
    private final CustomerDao customerDao;
    private final PasswordEncoder passwordEncoder;
    private final CustomerDTOMapper customerDTOMapper;
    public CustomerServices(@Qualifier("jdbc") CustomerDao customerDao, PasswordEncoder passwordEncoder, CustomerDTOMapper customerDTOMapper) {
        this.customerDao = customerDao;
        this.passwordEncoder = passwordEncoder;
        this.customerDTOMapper = customerDTOMapper;
    }

    public void registerCustomer(RegisterRequest registerRequest){

        String email = registerRequest.email();
        if (customerDao.existsPersonWithEmail(email)){
            throw new ResourceDuplicateFoundException("Email Already Taken!");
        }

        Customer customer=new Customer(
                registerRequest.name(),
                registerRequest.email(),
                passwordEncoder.encode(registerRequest.password()),
                registerRequest.age(),
                registerRequest.gender());

        customerDao.insertCustomer(customer);
    }
    public CustomerDTO getCustomerByEmail(String email){
        return customerDao.selectCustomerByEmail(email)
                .map(customerDTOMapper)
                .orElseThrow(()->new ResourceNotFoundException(
                        "Customer not found!"
                ));
    }

    public List<CustomerDTO> getCustomerList(){
        return customerDao.selectAllCustomers()
                .stream()
                .map(
                        customerDTOMapper
                ).collect(Collectors.toList());
    }

    public void updateCustomer(CustomerUpdateRequest customerUpdateRequest,String email){
        Customer customer = customerDao.selectCustomerByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException(
                        "customer with email [%s] not found!".formatted(email)
                ));
        boolean changes=false;
        if (customerUpdateRequest.name()!=null && !customerUpdateRequest.name().equals(customer.getName())){
            customer.setName(customerUpdateRequest.name());
            changes=true;
        }

        if (customerUpdateRequest.email()!=null && !customerUpdateRequest.email().equals(customer.getEmail())){
            if (customerDao.existsPersonWithEmail(customerUpdateRequest.email())){
                throw new ResourceDuplicateFoundException("email already taken!");
            }
            customer.setEmail(customerUpdateRequest.email());
            changes=true;
        }

        if (customerUpdateRequest.age()!=null  && !customerUpdateRequest.age().equals(customer.getAge())){
            customer.setAge(customerUpdateRequest.age());
            changes=true;
        }

        if (!changes){
            throw new RequestValidationException("no data changes found");
        }
        customerDao.updateCustomer(customer);
    }

    public void deleteCustomer(String email){
        if (!customerDao.existsPersonWithEmail(email)){
            throw new ResourceNotFoundException("Email Not Exists!");
        }
        customerDao.deleteCustomerByEmail(email);
    }
}
