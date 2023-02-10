package com.example.burgershop.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Table(name = "product")
@Entity(name = "Product")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @SequenceGenerator(name = "product_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "product_seq")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "typ")
    private String typ;

    @Column(name = "product_type", nullable = false)
    private String productType;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "descriere")
    private String descriere;

    @Column(name = "ingredients", nullable = false)
    private String ingredients;

    @Column(name = "src", nullable = false)
    private String src;


    @ManyToMany(mappedBy = "products", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<Customer> customers = new ArrayList<>();


    public Product(String name, String typ, String productType, Double price, String descriere, String ingredients, String src) {
        this.name = name;
        this.typ = typ;
        this.productType = productType;
        this.price = price;
        this.descriere = descriere;
        this.ingredients = ingredients;
        this.src = src;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(getName(), product.getName()) && Objects.equals(getTyp(), product.getTyp()) && Objects.equals(getProductType(), product.getProductType()) && Objects.equals(getPrice(), product.getPrice()) && Objects.equals(getDescriere(), product.getDescriere()) && Objects.equals(getIngredients(), product.getIngredients()) && Objects.equals(getSrc(), product.getSrc());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getTyp(), getProductType(), getPrice(), getDescriere(), getIngredients(), getSrc());
    }
}
