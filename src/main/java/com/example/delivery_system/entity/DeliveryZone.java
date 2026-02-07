package com.example.delivery_system.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "delivery_zones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryZone {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "zone_id")
    private UUID zoneId;

    @Column(name = "zone_name", nullable = false, length = 100)
    private String zoneName;

    @Column(name = "zone_code", unique = true, length = 20)
    private String zoneCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_zone_id")
    private DeliveryZone parentZone;

    @Column(name = "center_lat")
    private Double centerLat;

    @Column(name = "center_lng")
    private Double centerLng;

    @Column(name = "difficulty_rating")
    @Builder.Default
    private Integer difficultyRating = 1;

    @Column(name = "avg_delivery_time_mins")
    private Integer avgDeliveryTimeMins;

    @Column(name = "best_delivery_window", length = 50)
    private String bestDeliveryWindow;

    @Column(name = "road_condition", length = 20)
    @Builder.Default
    private String roadCondition = "good";

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}