package com.example.delivery_system.repository;

import com.example.delivery_system.entity.DeliveryRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface DeliveryRouteRepository extends JpaRepository<DeliveryRoute, UUID> {

    List<DeliveryRoute> findByDriverDriverId(UUID driverId);

    List<DeliveryRoute> findByRouteDate(LocalDate date);

    List<DeliveryRoute> findByStatus(String status);

    @Query("SELECT r FROM DeliveryRoute r WHERE r.driver.driverId = :driverId AND r.routeDate = :date")
    List<DeliveryRoute> findByDriverAndDate(@Param("driverId") UUID driverId, @Param("date") LocalDate date);

    @Query("SELECT r FROM DeliveryRoute r LEFT JOIN FETCH r.stops WHERE r.routeId = :id")
    DeliveryRoute findByIdWithStops(@Param("id") UUID id);
}
