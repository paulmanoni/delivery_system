package com.example.delivery_system.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "route_stops")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteStop {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "stop_id")
    private UUID stopId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private DeliveryRoute route;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "sequence_number", nullable = false)
    private Integer sequenceNumber;

    @Column(name = "planned_arrival")
    private LocalTime plannedArrival;

    @Column(name = "actual_arrival")
    private LocalDateTime actualArrival;

    @Column(name = "planned_duration_mins")
    @Builder.Default
    private Integer plannedDurationMins = 5;

    @Column(name = "actual_duration_mins")
    private Integer actualDurationMins;

    @Column(name = "delivery_lat")
    private Double deliveryLat;

    @Column(name = "delivery_lng")
    private Double deliveryLng;

    @Column(name = "location_accuracy_meters")
    private Double locationAccuracyMeters;

    @Column(length = 20)
    @Builder.Default
    private String status = "pending";

    @Column(name = "failure_reason", length = 100)
    private String failureReason;

    @Column(name = "recipient_name", length = 100)
    private String recipientName;

    @Column(name = "signature_url", length = 500)
    private String signatureUrl;

    @Column(name = "photo_proof_url", length = 500)
    private String photoProofUrl;

    @Column(name = "driver_notes", columnDefinition = "TEXT")
    private String driverNotes;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
}