package com.example.delivery_system.config;

import com.example.delivery_system.entity.*;
import com.example.delivery_system.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!test")
public class DataInitializer implements CommandLineRunner {

    private final DeliveryZoneRepository zoneRepository;
    private final LandmarkRepository landmarkRepository;
    private final CustomerRepository customerRepository;
    private final CustomerLocationRepository locationRepository;
    private final DriverRepository driverRepository;
    private final OrderRepository orderRepository;

    @Override
    public void run(String... args) {
        if (zoneRepository.count() > 0) {
            log.info("Database already initialized, skipping...");
            return;
        }

        log.info("Initializing sample data...");

        // Create Zones
        DeliveryZone downtown = zoneRepository.save(DeliveryZone.builder()
                .zoneName("Downtown")
                .zoneCode("DT-001")
                .centerLat(-1.2864)
                .centerLng(36.8172)
                .difficultyRating(2)
                .avgDeliveryTimeMins(15)
                .bestDeliveryWindow("morning")
                .roadCondition("good")
                .notes("Central business district, heavy traffic during rush hours")
                .build());

        DeliveryZone westlands = zoneRepository.save(DeliveryZone.builder()
                .zoneName("Westlands")
                .zoneCode("WL-001")
                .centerLat(-1.2673)
                .centerLng(36.8037)
                .difficultyRating(1)
                .avgDeliveryTimeMins(12)
                .bestDeliveryWindow("any")
                .roadCondition("good")
                .notes("Commercial and residential area, good road network")
                .build());

        DeliveryZone eastleigh = zoneRepository.save(DeliveryZone.builder()
                .zoneName("Eastleigh")
                .zoneCode("EL-001")
                .centerLat(-1.2720)
                .centerLng(36.8510)
                .difficultyRating(4)
                .avgDeliveryTimeMins(25)
                .bestDeliveryWindow("morning")
                .roadCondition("fair")
                .notes("Dense market area, narrow streets, use motorcycle for delivery")
                .build());

        DeliveryZone ruralArea = zoneRepository.save(DeliveryZone.builder()
                .zoneName("Kiambu Rural")
                .zoneCode("KR-001")
                .centerLat(-1.1714)
                .centerLng(36.8356)
                .difficultyRating(5)
                .avgDeliveryTimeMins(45)
                .bestDeliveryWindow("morning")
                .roadCondition("poor")
                .notes("Rural area, unpaved roads, landmarks essential for navigation")
                .build());

        // Create Landmarks
        landmarkRepository.save(Landmark.builder()
                .landmarkName("Central Market")
                .landmarkType("market")
                .zone(downtown)
                .latitude(-1.2850)
                .longitude(36.8260)
                .description("Large covered market, visible from main road")
                .reliabilityScore(5)
                .build());

        landmarkRepository.save(Landmark.builder()
                .landmarkName("Blue Mosque")
                .landmarkType("mosque")
                .zone(eastleigh)
                .latitude(-1.2725)
                .longitude(36.8520)
                .description("Large blue-domed mosque, major landmark")
                .reliabilityScore(5)
                .build());

        Landmark mangotree = landmarkRepository.save(Landmark.builder()
                .landmarkName("Old Mango Tree")
                .landmarkType("tree")
                .zone(ruralArea)
                .latitude(-1.1720)
                .longitude(36.8360)
                .description("Large mango tree at the junction")
                .reliabilityScore(4)
                .build());

        // Create Customers
        Customer john = customerRepository.save(Customer.builder()
                .fullName("John Kamau")
                .phonePrimary("+254712345678")
                .phoneSecondary("+254723456789")
                .preferredLanguage("en")
                .build());

        Customer mary = customerRepository.save(Customer.builder()
                .fullName("Mary Wanjiku")
                .phonePrimary("+254733456789")
                .preferredLanguage("sw")
                .build());

        Customer ahmed = customerRepository.save(Customer.builder()
                .fullName("Ahmed Hassan")
                .phonePrimary("+254744567890")
                .preferredLanguage("en")
                .build());

        // Create Customer Locations
        CustomerLocation johnHome = locationRepository.save(CustomerLocation.builder()
                .customer(john)
                .locationName("Home")
                .zone(westlands)
                .latitude(-1.2680)
                .longitude(36.8045)
                .accuracyMeters(10.0)
                .plusCode("6GCRMQRG+F9")
                .locationDescription("Blue gate, two-story building, bougainvillea flowers")
                .accessNotes("Ring bell twice, guard will open")
                .preferredTimeStart(LocalTime.of(9, 0))
                .preferredTimeEnd(LocalTime.of(17, 0))
                .parkingAvailable(true)
                .isDefault(true)
                .verificationStatus("verified")
                .confidenceScore(95)
                .build());

        CustomerLocation maryShop = locationRepository.save(CustomerLocation.builder()
                .customer(mary)
                .locationName("Shop")
                .zone(eastleigh)
                .latitude(-1.2730)
                .longitude(36.8515)
                .directionsFromLandmark("50m east of Blue Mosque, first floor")
                .locationDescription("Textile shop with green sign 'Mary Fashions'")
                .accessNotes("Call on arrival, busy market area")
                .preferredTimeStart(LocalTime.of(8, 0))
                .preferredTimeEnd(LocalTime.of(18, 0))
                .parkingAvailable(false)
                .isDefault(true)
                .verificationStatus("verified")
                .confidenceScore(85)
                .build());

        CustomerLocation ahmedFarm = locationRepository.save(CustomerLocation.builder()
                .customer(ahmed)
                .locationName("Farm")
                .zone(ruralArea)
                .latitude(-1.1725)
                .longitude(36.8365)
                .nearestLandmark(mangotree)
                .directionsFromLandmark("200m north of old mango tree, turn left at dirt road")
                .locationDescription("Farmhouse with red roof, chicken coop visible")
                .accessNotes("4x4 recommended during rainy season")
                .preferredTimeStart(LocalTime.of(7, 0))
                .preferredTimeEnd(LocalTime.of(12, 0))
                .parkingAvailable(true)
                .isDefault(true)
                .verificationStatus("unverified")
                .confidenceScore(60)
                .build());

        // Create Drivers
        driverRepository.save(Driver.builder()
                .fullName("Peter Ochieng")
                .phone("+254755678901")
                .licenseNumber("DL12345678")
                .vehicleType("motorcycle")
                .homeZone(downtown)
                .maxCapacityKg(BigDecimal.valueOf(50))
                .maxPackages(10)
                .rating(BigDecimal.valueOf(4.8))
                .totalDeliveries(234)
                .build());

        driverRepository.save(Driver.builder()
                .fullName("James Mwangi")
                .phone("+254766789012")
                .licenseNumber("DL87654321")
                .vehicleType("van")
                .homeZone(westlands)
                .maxCapacityKg(BigDecimal.valueOf(500))
                .maxPackages(50)
                .rating(BigDecimal.valueOf(4.5))
                .totalDeliveries(156)
                .build());

        driverRepository.save(Driver.builder()
                .fullName("David Kiprop")
                .phone("+254777890123")
                .vehicleType("bicycle")
                .homeZone(downtown)
                .maxCapacityKg(BigDecimal.valueOf(20))
                .maxPackages(5)
                .rating(BigDecimal.valueOf(4.9))
                .totalDeliveries(89)
                .build());

        // Create Orders
        orderRepository.save(Order.builder()
                .orderNumber("ORD-" + System.currentTimeMillis())
                .customer(john)
                .deliveryLocation(johnHome)
                .packageCount(2)
                .totalWeightKg(BigDecimal.valueOf(3.5))
                .packageSize("medium")
                .isFragile(false)
                .requestedDate(LocalDate.now())
                .timeWindowStart(LocalTime.of(10, 0))
                .timeWindowEnd(LocalTime.of(14, 0))
                .priority(2)
                .status("pending")
                .build());

        orderRepository.save(Order.builder()
                .orderNumber("ORD-" + (System.currentTimeMillis() + 1))
                .customer(mary)
                .deliveryLocation(maryShop)
                .packageCount(1)
                .totalWeightKg(BigDecimal.valueOf(0.5))
                .packageSize("small")
                .isPerishable(true)
                .requestedDate(LocalDate.now())
                .priority(1)
                .status("pending")
                .build());

        orderRepository.save(Order.builder()
                .orderNumber("ORD-" + (System.currentTimeMillis() + 2))
                .customer(ahmed)
                .deliveryLocation(ahmedFarm)
                .packageCount(5)
                .totalWeightKg(BigDecimal.valueOf(25.0))
                .packageSize("large")
                .codAmount(BigDecimal.valueOf(15000))
                .requestedDate(LocalDate.now().plusDays(1))
                .priority(3)
                .status("pending")
                .build());

        log.info("Sample data initialized successfully!");
    }
}
