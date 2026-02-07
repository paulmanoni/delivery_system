package com.example.delivery_system.service;

import com.example.delivery_system.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final OrderRepository orderRepository;
    private final DriverRepository driverRepository;
    private final CustomerRepository customerRepository;
    private final DeliveryRouteRepository routeRepository;
    private final DeliveryZoneRepository zoneRepository;

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("totalOrders", orderRepository.count());
        stats.put("pendingOrders", orderRepository.countByStatus("pending"));
        stats.put("inTransitOrders", orderRepository.countByStatus("in_transit"));
        stats.put("deliveredOrders", orderRepository.countByStatus("delivered"));
        stats.put("failedOrders", orderRepository.countByStatus("failed"));

        stats.put("totalDrivers", driverRepository.count());
        stats.put("activeDrivers", driverRepository.findByIsActiveTrue().size());

        stats.put("totalCustomers", customerRepository.count());
        stats.put("totalZones", zoneRepository.findByIsActiveTrue().size());

        stats.put("todayRoutes", routeRepository.findByRouteDate(LocalDate.now()).size());
        stats.put("activeRoutes", routeRepository.findByStatus("in_progress").size());

        return stats;
    }
}
