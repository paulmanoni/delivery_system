package com.example.delivery_system.repository;

import com.example.delivery_system.entity.DeliveryZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeliveryZoneRepository extends JpaRepository<DeliveryZone, UUID> {

    List<DeliveryZone> findByIsActiveTrue();

    List<DeliveryZone> findByParentZoneIsNull();

    List<DeliveryZone> findByParentZone(DeliveryZone parentZone);

    @Query("SELECT z FROM DeliveryZone z WHERE z.isActive = true ORDER BY z.zoneName")
    List<DeliveryZone> findAllActiveOrderByName();

    boolean existsByZoneCode(String zoneCode);
}