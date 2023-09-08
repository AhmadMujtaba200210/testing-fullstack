package com.amigoes.fullstack.customer;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/customer")
public class CustomerController {
    private final CustomerServices customerServices;

    public CustomerController(CustomerServices customerServices) {
        this.customerServices = customerServices;
    }

    @PostMapping("/register")
    public void createUser(@RequestBody RegisterRequest registerRequest){
        customerServices.registerCustomer(registerRequest);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<?> getUser(@PathVariable String email){
        return ResponseEntity.ok(customerServices.getCustomerByEmail(email));
    }

    @GetMapping
    public ResponseEntity<?> getUser(){
        return ResponseEntity.ok(customerServices.getCustomerList());
    }

    @PutMapping("/update/{email}")
    public void updateUser(@RequestBody CustomerUpdateRequest customerUpdateRequest, @PathVariable String email){
        customerServices.updateCustomer(customerUpdateRequest,email);
    }
    @DeleteMapping("/delete/{email}")
    public void deleteUser(@PathVariable String email){
        customerServices.deleteCustomer(email);
    }
}
