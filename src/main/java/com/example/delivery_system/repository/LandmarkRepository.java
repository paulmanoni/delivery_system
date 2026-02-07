package com.example.delivery_system.repository;

import com.example.delivery_system.entity.DeliveryZone;
import com.example.delivery_system.entity.Landmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LandmarkRepository extends JpaRepository<Landmark, UUID> {

    List<Landmark> findByZone(DeliveryZone zone);

    List<Landmark> findByZoneZoneId(UUID zoneId);

    List<Landmark> findByLandmarkType(String type);

    List<Landmark> findByIsPermanentTrue();
}