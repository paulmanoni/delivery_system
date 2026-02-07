package com.example.delivery_system.repository;

import com.example.delivery_system.entity.RouteStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RouteStopRepository extends JpaRepository<RouteStop, UUID> {

    List<RouteStop> findByRouteRouteIdOrderBySequenceNumber(UUID routeId);

    List<RouteStop> findByStatus(String status);

    @Query("SELECT rs FROM RouteStop rs WHERE rs.route.routeId = :routeId AND rs.status = 'pending' ORDER BY rs.sequenceNumber")
    List<RouteStop> findPendingStops(@Param("routeId") UUID routeId);

    @Query("SELECT COUNT(rs) FROM RouteStop rs WHERE rs.route.routeId = :routeId AND rs.status = 'delivered'")
    long countDeliveredStops(@Param("routeId") UUID routeId);
}
