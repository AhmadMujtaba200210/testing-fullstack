package com.amigoes.fullstack.customer;

import com.amigoes.fullstack.exception.RequestValidationException;
import com.amigoes.fullstack.exception.ResourceDuplicateFoundException;
import com.amigoes.fullstack.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServices {
    private final CustomerDao customerDao;

    public CustomerServices(@Qualifier("jdbc") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void registerCustomer(RegisterRequest registerRequest){

        String email = registerRequest.email();
        if (customerDao.existsPersonWithEmail(email)){
            throw new ResourceDuplicateFoundException("Email Already Taken!");
        }

        Customer customer=new Customer(
                registerRequest.name(),
                registerRequest.email(),
                registerRequest.age()
        );

        customerDao.insertCustomer(customer);
    }
    public Customer getCustomerByEmail(String email){
        return customerDao.selectCustomerByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException(
                        "Customer not found!"
                ));
    }

    public List<Customer> getCustomerList(){
        return customerDao.selectAllCustomers();
    }

    public void updateCustomer(CustomerUpdateRequest customerUpdateRequest,String email){
        Customer customer=getCustomerByEmail(email);

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
