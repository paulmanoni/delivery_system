package com.example.delivery_system.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "customer_locations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "location_id")
    private UUID locationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "location_name", length = 100)
    private String locationName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id")
    private DeliveryZone zone;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(name = "accuracy_meters")
    private Double accuracyMeters;

    @Column(name = "plus_code", length = 20)
    private String plusCode;

    @Column(name = "what3words", length = 100)
    private String what3words;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nearest_landmark_id")
    private Landmark nearestLandmark;

    @Column(name = "directions_from_landmark", columnDefinition = "TEXT")
    private String directionsFromLandmark;

    @Column(name = "location_description", columnDefinition = "TEXT")
    private String locationDescription;

    @Column(name = "access_notes", columnDefinition = "TEXT")
    private String accessNotes;

    @Column(name = "best_access_route", columnDefinition = "TEXT")
    private String bestAccessRoute;

    @Column(name = "parking_available")
    @Builder.Default
    private Boolean parkingAvailable = true;

    @Column(name = "preferred_time_start")
    private LocalTime preferredTimeStart;

    @Column(name = "preferred_time_end")
    private LocalTime preferredTimeEnd;

    @Column(name = "delivery_instructions", columnDefinition = "TEXT")
    private String deliveryInstructions;

    @Column(name = "verification_status", length = 20)
    @Builder.Default
    private String verificationStatus = "unverified";

    @Column(name = "confidence_score")
    @Builder.Default
    private Integer confidenceScore = 50;

    @Column(name = "is_default")
    @Builder.Default
    private Boolean isDefault = false;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
}