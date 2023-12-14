package com.amigoes.fullstack.customer;


import com.amigoes.fullstack.jwt.JWTUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/customer")
public class CustomerController {
    private final CustomerServices customerServices;
    private final JWTUtil jwtUtil;
    public CustomerController(CustomerServices customerServices, JWTUtil jwtUtil) {
        this.customerServices = customerServices;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody RegisterRequest registerRequest){
        customerServices.registerCustomer(registerRequest);
        String jwtToken=jwtUtil.issueToken(registerRequest.email(),"ROLE_USER");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION,jwtToken)
                .build();
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
