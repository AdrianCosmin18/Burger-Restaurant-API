package com.example.burgershop.service;

import com.example.burgershop.models.Product;
import com.example.burgershop.models.ProductDTO;
import com.example.burgershop.repos.ProductRepo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Modifying
    @Transactional
    public void addProduct(ProductDTO productDTO){
        if (productRepo.getProductsByName(productDTO.getName()).isPresent()){
            throw new RuntimeException("There is already a product with this name");
        }
        productRepo.save(new Product(productDTO.getName(), productDTO.getTyp(), productDTO.getProductType(), productDTO.getPrice(), productDTO.getDescriere(), productDTO.getIngredients(), productDTO.getSrc()));
    }

    public Product getProduct(Long id){
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("There is no product with this id"));
        return product;
    }

    public List<Product> getProducts(){
        List<Product> products = productRepo.findAll();
        if(products.isEmpty()){
            throw new RuntimeException("There is no product in db");
        }
        return products;
    }

    public void deleteProductById(Long id){
        if(productRepo.findById(id).isEmpty()){
            throw new RuntimeException("There is no product with this id: "+ id);
        }
        this.productRepo.deleteById(id);
    }

    public List<Product> getProductByProductType(String productType){

        List<Product> products = productRepo.getProductsByProductType(productType)
                .orElseThrow(() -> new RuntimeException("eroare"));
        return products;
    }

    public void updateProductSrcById(Long id, String src){
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("There is no product with this id"));
        product.setSrc(src);
        productRepo.save(product);
    }

    public Product getProductByName(String name){
        return productRepo.getProductsByName(name)
                .orElseThrow(() -> new RuntimeException("There is no product with this name" + name));
    }
}
