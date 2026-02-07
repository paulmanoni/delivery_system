package com.example.delivery_system.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "delivery_routes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "route_id")
    private UUID routeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(name = "route_date", nullable = false)
    private LocalDate routeDate;

    @Column(name = "total_stops")
    @Builder.Default
    private Integer totalStops = 0;

    @Column(name = "total_distance_km", precision = 10, scale = 2)
    private BigDecimal totalDistanceKm;

    @Column(name = "estimated_duration_mins")
    private Integer estimatedDurationMins;

    @Column(name = "actual_duration_mins")
    private Integer actualDurationMins;

    @Column(name = "optimization_score", precision = 5, scale = 2)
    private BigDecimal optimizationScore;

    @Column(name = "algorithm_version", length = 20)
    private String algorithmVersion;

    @Column(length = 20)
    @Builder.Default
    private String status = "planned";

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<RouteStop> stops = new ArrayList<>();

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}