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

    @GetMapping("/get-products-of-customer/{id}")
    public ResponseEntity<List<Product>> getProductsOfCustomerById(@PathVariable long id) {
        return new ResponseEntity<List<Product>>(this.customerService.getProductsOfCustomerById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable long id) {
        return new ResponseEntity<Customer>(this.customerService.getCustomerById(id), HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<Customer> getCustomerByAuthentication(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password){
        return new ResponseEntity<>(this.customerService.getCustomerByEmailAndPassword(email, password), HttpStatus.OK);
    }

    @PostMapping("/add-to-order/{customerId}/{productId}")
    public void addToOrder(@PathVariable(value = "customerId") long customerId, @PathVariable(value = "productId") long productId){
        this.customerService.addProductToOrder(customerId, productId);
    }

    @DeleteMapping("/delete-from-cart/{customerId}/{productId}")
    public void deleteFromOrder(@PathVariable(value = "customerId") long customerId, @PathVariable(value = "productId") long productId){
        this.customerService.deleteProductFromOrder(customerId, productId);
    }

    @DeleteMapping("/place-order/{id}")
    public void placeOrder(@PathVariable long id){
        this.customerService.placeOrder(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable long id){
        this.customerService.deleteCustomer(id);
    }

    @PutMapping("/{email}")
    public void updateCustomer(@PathVariable String email, @RequestBody CustomerDTO customerDTO){
        this.customerService.updateCustomerByEmail(email, customerDTO);
    }

    @DeleteMapping("/delete-customer/{email}")
    public void deleteCustomer(@PathVariable String email){
        this.customerService.deleteCustomerByEmail(email);
    }

}
