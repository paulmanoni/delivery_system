package com.example.delivery_system.repository;

import com.example.delivery_system.entity.CustomerLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerLocationRepository extends JpaRepository<CustomerLocation, UUID> {

    List<CustomerLocation> findByCustomerCustomerId(UUID customerId);

    List<CustomerLocation> findByZoneZoneId(UUID zoneId);

    @Query("SELECT l FROM CustomerLocation l WHERE l.isActive = true AND l.customer.customerId = :customerId")
    List<CustomerLocation> findActiveByCustomer(@Param("customerId") UUID customerId);

    @Query("SELECT l FROM CustomerLocation l WHERE l.verificationStatus = :status")
    List<CustomerLocation> findByVerificationStatus(@Param("status") String status);
}