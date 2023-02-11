package com.example.burgershop.service;

import com.example.burgershop.models.Customer;
import com.example.burgershop.models.CustomerDTO;
import com.example.burgershop.models.Product;
import com.example.burgershop.repos.CustomerRepo;
import com.example.burgershop.repos.ProductRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepo customerRepo;
    private ProductRepo productRepo;

    public CustomerService(CustomerRepo customerRepo, ProductRepo productRepo) {
        this.customerRepo = customerRepo;
        this.productRepo = productRepo;
    }

    @Modifying
    @Transactional
    public void addCustomer(CustomerDTO customerDTO){
        if (customerRepo.getCustomersByEmail(customerDTO.getEmail()).isPresent()){
            throw new RuntimeException("This email is assigned to another account");
        }
        customerRepo.save(new Customer(customerDTO.getFullName(), customerDTO.getEmail(), customerDTO.getPassword()));
    }

    public Customer getCustomerByEmailAndPassword(String email, String password){
        Customer customer = this.customerRepo.getCustomersByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("Email or password incorrect"));
        return customer;
    }

    public Customer getCustomerById(long id){
        return customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Theree is no customer with this id"));
    }

    public List<Product> getProductsOfCustomerById(long id){
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Theree is no customer with this id"));
        return customer.getProducts();
    }

    public void deleteCustomer(long id){
        Optional<Customer> customer = customerRepo.findById(id);
        if (customer.equals(Optional.empty())){
            throw new RuntimeException("There is no customer with this id:" + id);
        }
        customerRepo.deleteById(id);
    }

    public void updateCustomer(long id, CustomerDTO customerDTO){

        Customer customer = customerRepo.findById(id).orElseThrow(() -> new RuntimeException("There is no customer with this id:" + id));

        if(customer.getEmail().equals(customerDTO.getEmail())){
            customerRepo.updateById(id, customerDTO.getFullName(), customerDTO.getEmail(), customerDTO.getPassword());
            return;
        } else if (customerRepo.getCustomersByEmail(customerDTO.getEmail()).isPresent()){
            throw new RuntimeException("This email is assigned to another account");
        }
        customerRepo.updateById(id, customerDTO.getFullName(), customerDTO.getEmail(), customerDTO.getPassword());
    }

    public List<Customer> getCustomers(){
        List<Customer> customers = customerRepo.findAll();
        if(customers.isEmpty()){
            throw new RuntimeException("There is no account create yet");
        }
        return customers;
    }

    public void addProductToOrder(long id, long productId){

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("There is no product with this id: " + productId));

        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("There is no customer with this id: "+ id));

        customer.addProduct(product);
        customerRepo.save(customer);
    }

    public void deleteProductFromOrder(long id, long productId){

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("There is no product with this id: " + productId));

        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("There is no customer with this id: "+ id));

        List<Product> products = customer.getProducts();
        if(products.stream().anyMatch( p -> p.getId() == productId)){
            products.remove(product);
            customerRepo.save(customer);
        }
        else{
            throw new RuntimeException("Something went wrong");
        }
    }

    public void placeOrder(long id){
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("There is no customer with this id: "+ id));
        customer.setProducts(new ArrayList<>());
        customerRepo.save(customer);
    }
}
