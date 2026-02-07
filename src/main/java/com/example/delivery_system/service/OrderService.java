package com.example.delivery_system.service;

import com.example.delivery_system.entity.Order;
import com.example.delivery_system.repository.OrderRepository;
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
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(UUID id) {
        return orderRepository.findById(id);
    }

    public Optional<Order> findByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    public List<Order> findByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    public List<Order> findByCustomer(UUID customerId) {
        return orderRepository.findByCustomerCustomerId(customerId);
    }

    public List<Order> findPendingForDate(LocalDate date) {
        return orderRepository.findPendingByDate(date);
    }

    public List<Order> findUndelivered() {
        return orderRepository.findUndeliveredOrders();
    }

    public Order save(Order order) {
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public void updateStatus(UUID orderId, String status) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatus(status);
            order.setUpdatedAt(LocalDateTime.now());
            orderRepository.save(order);
        });
    }

    public void delete(UUID id) {
        orderRepository.deleteById(id);
    }

    public long countByStatus(String status) {
        return orderRepository.countByStatus(status);
    }

    public String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis();
    }
}
