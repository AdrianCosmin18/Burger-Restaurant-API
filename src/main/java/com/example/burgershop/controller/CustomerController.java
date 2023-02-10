package com.example.burgershop.controller;

import com.example.burgershop.models.Customer;
import com.example.burgershop.models.CustomerDTO;
import com.example.burgershop.models.Product;
import com.example.burgershop.models.ProductDTO;
import com.example.burgershop.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping("burger-shop/customer-controller")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping()
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerDTO customerDTO){
        this.customerService.addCustomer(customerDTO);
        return new ResponseEntity<CustomerDTO>(customerDTO, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Customer>> getCustomers() {
        return new ResponseEntity<List<Customer>>(this.customerService.getCustomers(), HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<Customer> getCustomerByAuthentication(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password){
        return new ResponseEntity<>(this.customerService.getCustomerByEmailAndPassword(email, password), HttpStatus.OK);
    }

    @PostMapping("/add-to-order/{id}")
    public ResponseEntity<String> addToOrder(@PathVariable long id, @RequestParam(value = "productName") String productName){
        this.customerService.addProductToOrder(id, productName);
        return new ResponseEntity<>(productName + " added to cart.", HttpStatus.OK);
    }

    @DeleteMapping("/delete-from-cart/{id}")
    public ResponseEntity<String> deleteFromOrder(@PathVariable long id, @RequestParam(value = "productName") String productName){
        this.customerService.deleteProductFromOrder(id, productName);
        return new ResponseEntity<>(productName + " deleted from order.", HttpStatus.OK);
    }

}
