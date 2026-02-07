package com.example.delivery_system.repository;

import com.example.delivery_system.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DriverRepository extends JpaRepository<Driver, UUID> {

    List<Driver> findByIsActiveTrue();

    List<Driver> findByVehicleType(String vehicleType);

    @Query("SELECT d FROM Driver d WHERE d.isActive = true AND d.homeZone.zoneId = :zoneId")
    List<Driver> findActiveByHomeZone(@Param("zoneId") UUID zoneId);

    @Query("SELECT d FROM Driver d WHERE d.isActive = true ORDER BY d.rating DESC")
    List<Driver> findTopRatedDrivers();
}