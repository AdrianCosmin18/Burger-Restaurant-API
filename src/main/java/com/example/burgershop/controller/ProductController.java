package com.example.burgershop.controller;

import com.example.burgershop.models.Product;
import com.example.burgershop.models.ProductDTO;
import com.example.burgershop.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping("burger-shop/products-controller")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO product){
        this.productService.addProduct(product);
        return new ResponseEntity<ProductDTO>(product, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getProduct(){
        return  new ResponseEntity<List<Product>>(this.productService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id){
        return new ResponseEntity<Product>(this.productService.getProduct(id), HttpStatus.OK);
    }

    @GetMapping("/get-product-by-productType")
    public ResponseEntity<List<Product>> getProductById(@RequestParam(name = "productType") String productType){
        return new ResponseEntity<List<Product>>(this.productService.getProductByProductType(productType), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable long id){
        this.productService.deleteProductById(id);
        return new ResponseEntity<String>("product deleted", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProductSrcById(@PathVariable long id, @RequestParam(name = "src") String src){
        this.productService.updateProductSrcById(id, src);
        return new ResponseEntity<>("src updated", HttpStatus.OK);
    }
}
