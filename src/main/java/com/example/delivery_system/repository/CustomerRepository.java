package com.example.delivery_system.repository;

import com.example.delivery_system.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByPhonePrimary(String phone);

    @Query("SELECT c FROM Customer c WHERE c.fullName LIKE %:name%")
    List<Customer> searchByName(@Param("name") String name);

    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.locations WHERE c.customerId = :id")
    Optional<Customer> findByIdWithLocations(@Param("id") UUID id);
}