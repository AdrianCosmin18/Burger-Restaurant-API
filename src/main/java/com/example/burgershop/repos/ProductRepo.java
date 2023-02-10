package com.example.burgershop.repos;

import com.example.burgershop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    Optional<Product> getProductsByName(String name);

    Optional<List<Product>> getProductsByProductType(String name);

    @Modifying
    @Transactional
    @Query("update Product p set p.src = ?2 where p.id = ?1")
    void updateProductSrcById(Long id, String src);
}
