package com.example.delivery_system.service;

import com.example.delivery_system.entity.DeliveryRoute;
import com.example.delivery_system.entity.Order;
import com.example.delivery_system.entity.RouteStop;
import com.example.delivery_system.repository.DeliveryRouteRepository;
import com.example.delivery_system.repository.OrderRepository;
import com.example.delivery_system.repository.RouteStopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RouteService {

    private final DeliveryRouteRepository routeRepository;
    private final RouteStopRepository stopRepository;
    private final OrderRepository orderRepository;

    public List<DeliveryRoute> findAll() {
        return routeRepository.findAll();
    }

    public Optional<DeliveryRoute> findById(UUID id) {
        return routeRepository.findById(id);
    }

    public DeliveryRoute findByIdWithStops(UUID id) {
        return routeRepository.findByIdWithStops(id);
    }

    public List<DeliveryRoute> findByDriver(UUID driverId) {
        return routeRepository.findByDriverDriverId(driverId);
    }

    public List<DeliveryRoute> findByDate(LocalDate date) {
        return routeRepository.findByRouteDate(date);
    }

    public List<DeliveryRoute> findByStatus(String status) {
        return routeRepository.findByStatus(status);
    }

    public DeliveryRoute save(DeliveryRoute route) {
        return routeRepository.save(route);
    }

    public DeliveryRoute saveWithStops(DeliveryRoute route, List<UUID> orderIds) {
        DeliveryRoute saved = routeRepository.save(route);
        if (orderIds != null && !orderIds.isEmpty()) {
            int seq = 1;
            for (UUID orderId : orderIds) {
                Order order = orderRepository.findById(orderId).orElse(null);
                if (order != null) {
                    RouteStop stop = RouteStop.builder()
                            .route(saved)
                            .order(order)
                            .sequenceNumber(seq++)
                            .status("pending")
                            .build();
                    if (order.getDeliveryLocation() != null) {
                        stop.setDeliveryLat(order.getDeliveryLocation().getLatitude());
                        stop.setDeliveryLng(order.getDeliveryLocation().getLongitude());
                    }
                    stopRepository.save(stop);
                    order.setStatus("assigned");
                    orderRepository.save(order);
                }
            }
            saved.setTotalStops(seq - 1);
            routeRepository.save(saved);
        }
        return saved;
    }

    public void startRoute(UUID routeId) {
        routeRepository.findById(routeId).ifPresent(route -> {
            route.setStatus("in_progress");
            route.setStartedAt(LocalDateTime.now());
            routeRepository.save(route);
        });
    }

    public void completeRoute(UUID routeId) {
        routeRepository.findById(routeId).ifPresent(route -> {
            route.setStatus("completed");
            route.setCompletedAt(LocalDateTime.now());
            routeRepository.save(route);
        });
    }

    public void delete(UUID id) {
        routeRepository.deleteById(id);
    }

    public List<RouteStop> findStopsByRoute(UUID routeId) {
        return stopRepository.findByRouteRouteIdOrderBySequenceNumber(routeId);
    }

    public RouteStop saveStop(RouteStop stop) {
        stop.setUpdatedAt(LocalDateTime.now());
        return stopRepository.save(stop);
    }

    public void updateStopStatus(UUID stopId, String status, String failureReason) {
        stopRepository.findById(stopId).ifPresent(stop -> {
            stop.setStatus(status);
            if (failureReason != null) {
                stop.setFailureReason(failureReason);
            }
            if ("delivered".equals(status) || "failed".equals(status)) {
                stop.setActualArrival(LocalDateTime.now());
            }
            stop.setUpdatedAt(LocalDateTime.now());
            stopRepository.save(stop);
        });
    }
}
