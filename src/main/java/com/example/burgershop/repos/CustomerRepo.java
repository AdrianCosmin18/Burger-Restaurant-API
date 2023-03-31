package com.example.burgershop.repos;

import com.example.burgershop.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Optional<Customer> getCustomersByEmailAndPassword(String email, String password);
    Optional<Customer> getCustomersByEmail(String email);

    @Transactional
    @Modifying
    @Query("update Customer c set c.email = ?3, c.fullName = ?2, c.password = ?4 where c.id = ?1")
    void updateById(long id, String fullName, String email, String password);

    @Modifying
    @Transactional
    @Query("update Customer c set c.fullName = ?2, c.email = ?3, c.password = ?4 where c.email = ?1")
    void updateCustomerByEmail(String oldEmail, String fullName, String newEmail, String password);

    @Modifying
    @Transactional
    void deleteByEmail(String email);

}
