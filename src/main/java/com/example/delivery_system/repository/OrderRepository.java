package com.example.delivery_system.repository;

import com.example.delivery_system.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    Optional<Order> findByOrderNumber(String orderNumber);

    List<Order> findByStatus(String status);

    List<Order> findByCustomerCustomerId(UUID customerId);

    @Query("SELECT o FROM Order o WHERE o.requestedDate = :date AND o.status = 'pending'")
    List<Order> findPendingByDate(@Param("date") LocalDate date);

    @Query("SELECT o FROM Order o WHERE o.status IN ('pending', 'assigned') ORDER BY o.priority, o.orderDate")
    List<Order> findUndeliveredOrders();

    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = :status")
    long countByStatus(@Param("status") String status);
}
