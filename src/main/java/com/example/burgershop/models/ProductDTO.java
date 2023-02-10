package com.example.burgershop.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private String name;
    private String typ;
    private String productType;
    private Double price;
    private String descriere;
    private String ingredients;
    private String src;

    public ProductDTO(String name, String productType, Double price, String ingredients, String src) {
        this.name = name;
        this.productType = productType;
        this.price = price;
        this.ingredients = ingredients;
        this.src = src;
    }
}
