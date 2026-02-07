package com.example.delivery_system.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "order_number", unique = true, nullable = false, length = 30)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_location_id", nullable = false)
    private CustomerLocation deliveryLocation;

    @Column(name = "package_count")
    @Builder.Default
    private Integer packageCount = 1;

    @Column(name = "total_weight_kg", precision = 8, scale = 2)
    private BigDecimal totalWeightKg;

    @Column(name = "package_size", length = 20)
    private String packageSize;

    @Column(name = "is_fragile")
    @Builder.Default
    private Boolean isFragile = false;

    @Column(name = "is_perishable")
    @Builder.Default
    private Boolean isPerishable = false;

    @Column(name = "cod_amount", precision = 12, scale = 2)
    private BigDecimal codAmount;

    @Column(name = "order_date")
    @Builder.Default
    private LocalDateTime orderDate = LocalDateTime.now();

    @Column(name = "requested_date")
    private LocalDate requestedDate;

    @Column(name = "time_window_start")
    private LocalTime timeWindowStart;

    @Column(name = "time_window_end")
    private LocalTime timeWindowEnd;

    @Column
    @Builder.Default
    private Integer priority = 2;

    @Column(length = 30)
    @Builder.Default
    private String status = "pending";

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
}