package com.example.delivery_system.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "landmarks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Landmark {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "landmark_id")
    private UUID landmarkId;

    @Column(name = "landmark_name", nullable = false, length = 150)
    private String landmarkName;

    @Column(name = "landmark_type", length = 50)
    private String landmarkType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id")
    private DeliveryZone zone;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "photo_url", length = 500)
    private String photoUrl;

    @Column(name = "reliability_score")
    @Builder.Default
    private Integer reliabilityScore = 5;

    @Column(name = "is_permanent")
    @Builder.Default
    private Boolean isPermanent = true;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}