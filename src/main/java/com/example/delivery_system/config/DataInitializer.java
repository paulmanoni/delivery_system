package com.example.delivery_system.config;

import com.example.delivery_system.entity.*;
import com.example.delivery_system.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private final DeliveryRouteRepository routeRepository;
    private final RouteStopRepository stopRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seedUsers();

        if (zoneRepository.count() > 0) {
            log.info("Database already initialized, skipping...");
            return;
        }

        log.info("Initializing sample data for Dodoma Region, Tanzania...");

        // ════════════════════════════════════════════════════════
        // DELIVERY ZONES — Dodoma Region
        // ════════════════════════════════════════════════════════
        DeliveryZone dodomaCbd = zoneRepository.save(DeliveryZone.builder()
                .zoneName("Dodoma CBD")
                .zoneCode("DD-001")
                .centerLat(-6.1630)
                .centerLng(35.7516)
                .difficultyRating(2)
                .avgDeliveryTimeMins(12)
                .bestDeliveryWindow("morning")
                .roadCondition("good")
                .notes("Central business district around Nyerere Square, paved roads, some traffic during market hours")
                .build());

        DeliveryZone chamwino = zoneRepository.save(DeliveryZone.builder()
                .zoneName("Chamwino")
                .zoneCode("CH-001")
                .centerLat(-6.1100)
                .centerLng(35.7680)
                .difficultyRating(4)
                .avgDeliveryTimeMins(35)
                .bestDeliveryWindow("morning")
                .roadCondition("fair")
                .notes("Semi-rural area north of Dodoma, mix of tarmac and murram roads, landmarks essential")
                .build());

        DeliveryZone miyujiMkulu = zoneRepository.save(DeliveryZone.builder()
                .zoneName("Miyuji Mkulu")
                .zoneCode("MM-001")
                .centerLat(-6.1880)
                .centerLng(35.7780)
                .difficultyRating(3)
                .avgDeliveryTimeMins(20)
                .bestDeliveryWindow("any")
                .roadCondition("good")
                .notes("Growing residential area east of CBD, new housing estates, mostly paved roads")
                .build());

        DeliveryZone nzuguni = zoneRepository.save(DeliveryZone.builder()
                .zoneName("Nzuguni")
                .zoneCode("NZ-001")
                .centerLat(-6.1370)
                .centerLng(35.7190)
                .difficultyRating(5)
                .avgDeliveryTimeMins(45)
                .bestDeliveryWindow("morning")
                .roadCondition("poor")
                .notes("Western outskirts, unpaved roads beyond main junction, use motorcycle or 4x4 during rains")
                .build());

        DeliveryZone viwandani = zoneRepository.save(DeliveryZone.builder()
                .zoneName("Viwandani")
                .zoneCode("VW-001")
                .centerLat(-6.1750)
                .centerLng(35.7350)
                .difficultyRating(2)
                .avgDeliveryTimeMins(15)
                .bestDeliveryWindow("any")
                .roadCondition("good")
                .notes("Industrial area south-west of CBD, wide roads, easy truck access, warehouses and factories")
                .build());

        DeliveryZone kikuyu = zoneRepository.save(DeliveryZone.builder()
                .zoneName("Kikuyu / Area C")
                .zoneCode("KC-001")
                .centerLat(-6.1520)
                .centerLng(35.7650)
                .difficultyRating(3)
                .avgDeliveryTimeMins(18)
                .bestDeliveryWindow("morning")
                .roadCondition("good")
                .notes("Residential area east of CBD near University of Dodoma, student population, mixed housing")
                .build());

        // ════════════════════════════════════════════════════════
        // LANDMARKS
        // ════════════════════════════════════════════════════════
        Landmark nyerereSquare = landmarkRepository.save(Landmark.builder()
                .landmarkName("Nyerere Square")
                .landmarkType("plaza")
                .zone(dodomaCbd)
                .latitude(-6.1628)
                .longitude(35.7520)
                .description("Central roundabout with Nyerere monument, visible from all directions")
                .reliabilityScore(5)
                .build());

        Landmark dodomaCathedral = landmarkRepository.save(Landmark.builder()
                .landmarkName("Dodoma Cathedral")
                .landmarkType("church")
                .zone(dodomaCbd)
                .latitude(-6.1645)
                .longitude(35.7490)
                .description("Large cathedral with twin towers near the market")
                .reliabilityScore(5)
                .build());

        landmarkRepository.save(Landmark.builder()
                .landmarkName("Jamatini Mosque")
                .landmarkType("mosque")
                .zone(dodomaCbd)
                .latitude(-6.1618)
                .longitude(35.7535)
                .description("Historic mosque with tall minaret, central Dodoma")
                .reliabilityScore(5)
                .build());

        Landmark chamwinoSchool = landmarkRepository.save(Landmark.builder()
                .landmarkName("Chamwino Secondary School")
                .landmarkType("school")
                .zone(chamwino)
                .latitude(-6.1085)
                .longitude(35.7695)
                .description("Large school compound with green gate, well-known in the area")
                .reliabilityScore(4)
                .build());

        landmarkRepository.save(Landmark.builder()
                .landmarkName("Chamwino District Office")
                .landmarkType("government")
                .zone(chamwino)
                .latitude(-6.1110)
                .longitude(35.7670)
                .description("District commissioner's office compound, Tanzanian flag visible")
                .reliabilityScore(5)
                .build());

        Landmark baobabTree = landmarkRepository.save(Landmark.builder()
                .landmarkName("Old Baobab Tree")
                .landmarkType("tree")
                .zone(nzuguni)
                .latitude(-6.1375)
                .longitude(35.7195)
                .description("Ancient baobab tree at the main junction, impossible to miss")
                .reliabilityScore(5)
                .build());

        Landmark nzuguniMosque = landmarkRepository.save(Landmark.builder()
                .landmarkName("Nzuguni Mosque")
                .landmarkType("mosque")
                .zone(nzuguni)
                .latitude(-6.1360)
                .longitude(35.7185)
                .description("White mosque with green dome near the main road")
                .reliabilityScore(5)
                .build());

        Landmark miyujiMarket = landmarkRepository.save(Landmark.builder()
                .landmarkName("Miyuji Market")
                .landmarkType("market")
                .zone(miyujiMkulu)
                .latitude(-6.1875)
                .longitude(35.7790)
                .description("Daily open-air market with corrugated iron stalls")
                .reliabilityScore(4)
                .build());

        landmarkRepository.save(Landmark.builder()
                .landmarkName("Miyuji Primary School")
                .landmarkType("school")
                .zone(miyujiMkulu)
                .latitude(-6.1895)
                .longitude(35.7770)
                .description("Primary school with yellow walls, near the market")
                .reliabilityScore(4)
                .build());

        Landmark udomGate = landmarkRepository.save(Landmark.builder()
                .landmarkName("UDOM Main Gate")
                .landmarkType("university")
                .zone(kikuyu)
                .latitude(-6.1510)
                .longitude(35.7665)
                .description("University of Dodoma main entrance, large signboard visible from road")
                .reliabilityScore(5)
                .build());

        landmarkRepository.save(Landmark.builder()
                .landmarkName("Viwandani Roundabout")
                .landmarkType("junction")
                .zone(viwandani)
                .latitude(-6.1745)
                .longitude(35.7355)
                .description("Main roundabout at the entrance to the industrial area")
                .reliabilityScore(5)
                .build());

        // ════════════════════════════════════════════════════════
        // CUSTOMERS — Tanzanian names
        // ════════════════════════════════════════════════════════
        Customer juma = customerRepository.save(Customer.builder()
                .fullName("Juma Abdallah")
                .phonePrimary("+255742123456")
                .phoneSecondary("+255682123456")
                .preferredLanguage("sw")
                .build());

        Customer halima = customerRepository.save(Customer.builder()
                .fullName("Halima Mwinyimkuu")
                .phonePrimary("+255754234567")
                .preferredLanguage("sw")
                .build());

        Customer emmanuel = customerRepository.save(Customer.builder()
                .fullName("Emmanuel Mazengo")
                .phonePrimary("+255763345678")
                .preferredLanguage("en")
                .build());

        Customer rehema = customerRepository.save(Customer.builder()
                .fullName("Rehema Saidi")
                .phonePrimary("+255715456789")
                .preferredLanguage("sw")
                .build());

        Customer fadhili = customerRepository.save(Customer.builder()
                .fullName("Fadhili Mwamba")
                .phonePrimary("+255786567890")
                .phoneSecondary("+255657567890")
                .preferredLanguage("sw")
                .build());

        Customer grace = customerRepository.save(Customer.builder()
                .fullName("Grace Kimaro")
                .phonePrimary("+255748678901")
                .preferredLanguage("en")
                .build());

        Customer bakari = customerRepository.save(Customer.builder()
                .fullName("Bakari Moshi")
                .phonePrimary("+255729789012")
                .preferredLanguage("sw")
                .build());

        Customer amina = customerRepository.save(Customer.builder()
                .fullName("Amina Lugendo")
                .phonePrimary("+255751890123")
                .preferredLanguage("sw")
                .build());

        // ════════════════════════════════════════════════════════
        // CUSTOMER LOCATIONS — Dodoma coordinates
        // ════════════════════════════════════════════════════════

        // Juma — CBD home + Chamwino office
        CustomerLocation jumaHome = locationRepository.save(CustomerLocation.builder()
                .customer(juma)
                .locationName("Nyumba - CBD")
                .zone(dodomaCbd)
                .latitude(-6.1635)
                .longitude(35.7525)
                .accuracyMeters(8.0)
                .locationDescription("Yellow house with blue door, opposite the post office")
                .accessNotes("Call on arrival, second floor apartment")
                .preferredTimeStart(LocalTime.of(8, 0))
                .preferredTimeEnd(LocalTime.of(17, 0))
                .parkingAvailable(true)
                .isDefault(true)
                .verificationStatus("verified")
                .confidenceScore(95)
                .build());

        locationRepository.save(CustomerLocation.builder()
                .customer(juma)
                .locationName("Ofisi - Chamwino")
                .zone(chamwino)
                .latitude(-6.1095)
                .longitude(35.7690)
                .locationDescription("Small office next to Chamwino Secondary School")
                .accessNotes("Weekdays only")
                .preferredTimeStart(LocalTime.of(9, 0))
                .preferredTimeEnd(LocalTime.of(15, 0))
                .parkingAvailable(true)
                .isDefault(false)
                .verificationStatus("verified")
                .confidenceScore(80)
                .build());

        // Halima — CBD shop
        CustomerLocation halimaShop = locationRepository.save(CustomerLocation.builder()
                .customer(halima)
                .locationName("Duka la Nguo")
                .zone(dodomaCbd)
                .latitude(-6.1650)
                .longitude(35.7505)
                .nearestLandmark(dodomaCathedral)
                .directionsFromLandmark("100m south of Dodoma Cathedral, right side of road")
                .locationDescription("Textile shop with red sign 'Halima Fashions'")
                .accessNotes("Open Mon-Sat, call before noon deliveries")
                .preferredTimeStart(LocalTime.of(9, 0))
                .preferredTimeEnd(LocalTime.of(18, 0))
                .parkingAvailable(false)
                .isDefault(true)
                .verificationStatus("verified")
                .confidenceScore(90)
                .build());

        // Emmanuel — Miyuji office
        CustomerLocation emmanuelOffice = locationRepository.save(CustomerLocation.builder()
                .customer(emmanuel)
                .locationName("Ofisi - Miyuji")
                .zone(miyujiMkulu)
                .latitude(-6.1885)
                .longitude(35.7785)
                .nearestLandmark(miyujiMarket)
                .locationDescription("White two-story office building near Miyuji Market")
                .accessNotes("Reception on ground floor, ask for Emmanuel")
                .preferredTimeStart(LocalTime.of(8, 0))
                .preferredTimeEnd(LocalTime.of(16, 0))
                .parkingAvailable(true)
                .isDefault(true)
                .verificationStatus("verified")
                .confidenceScore(88)
                .build());

        // Rehema — Nzuguni farm
        CustomerLocation rehemaFarm = locationRepository.save(CustomerLocation.builder()
                .customer(rehema)
                .locationName("Shamba - Nzuguni")
                .zone(nzuguni)
                .latitude(-6.1380)
                .longitude(35.7200)
                .nearestLandmark(baobabTree)
                .directionsFromLandmark("300m north of the old baobab tree, turn right at dirt path")
                .locationDescription("Farmhouse with corrugated iron roof, sunflower field nearby")
                .accessNotes("4x4 needed during rainy season, call ahead")
                .preferredTimeStart(LocalTime.of(7, 0))
                .preferredTimeEnd(LocalTime.of(12, 0))
                .parkingAvailable(true)
                .isDefault(true)
                .verificationStatus("unverified")
                .confidenceScore(55)
                .build());

        // Fadhili — Viwandani warehouse
        CustomerLocation fadhiliWarehouse = locationRepository.save(CustomerLocation.builder()
                .customer(fadhili)
                .locationName("Ghala - Viwandani")
                .zone(viwandani)
                .latitude(-6.1755)
                .longitude(35.7360)
                .locationDescription("Large grey warehouse with 'Mwamba Trading' sign, loading bay on left")
                .accessNotes("Use side entrance for parcels, main gate for trucks")
                .preferredTimeStart(LocalTime.of(7, 0))
                .preferredTimeEnd(LocalTime.of(16, 0))
                .parkingAvailable(true)
                .isDefault(true)
                .verificationStatus("verified")
                .confidenceScore(92)
                .build());

        // Grace — Kikuyu / Area C near UDOM
        CustomerLocation graceHome = locationRepository.save(CustomerLocation.builder()
                .customer(grace)
                .locationName("Nyumba - Kikuyu")
                .zone(kikuyu)
                .latitude(-6.1525)
                .longitude(35.7660)
                .nearestLandmark(udomGate)
                .directionsFromLandmark("200m past UDOM main gate, turn left, pink house with mango tree")
                .locationDescription("Pink house with mango tree in front yard, near UDOM")
                .accessNotes("Ring bell at gate, dogs in compound")
                .preferredTimeStart(LocalTime.of(10, 0))
                .preferredTimeEnd(LocalTime.of(18, 0))
                .parkingAvailable(true)
                .isDefault(true)
                .verificationStatus("verified")
                .confidenceScore(85)
                .build());

        // Bakari — Nzuguni shop
        CustomerLocation bakariShop = locationRepository.save(CustomerLocation.builder()
                .customer(bakari)
                .locationName("Duka - Nzuguni")
                .zone(nzuguni)
                .latitude(-6.1365)
                .longitude(35.7188)
                .nearestLandmark(nzuguniMosque)
                .directionsFromLandmark("50m east of Nzuguni Mosque, ground floor shop")
                .locationDescription("Hardware shop with green door, next to the mosque")
                .accessNotes("Open daily 7am-7pm")
                .preferredTimeStart(LocalTime.of(7, 0))
                .preferredTimeEnd(LocalTime.of(19, 0))
                .parkingAvailable(false)
                .isDefault(true)
                .verificationStatus("verified")
                .confidenceScore(88)
                .build());

        // Amina — Chamwino home
        CustomerLocation aminaHome = locationRepository.save(CustomerLocation.builder()
                .customer(amina)
                .locationName("Nyumba - Chamwino")
                .zone(chamwino)
                .latitude(-6.1115)
                .longitude(35.7675)
                .nearestLandmark(chamwinoSchool)
                .directionsFromLandmark("400m west of Chamwino Secondary School, white house with red roof")
                .locationDescription("White house with red corrugated roof, banana plants at entrance")
                .accessNotes("Call before arrival, no gate number")
                .preferredTimeStart(LocalTime.of(8, 0))
                .preferredTimeEnd(LocalTime.of(14, 0))
                .parkingAvailable(true)
                .isDefault(true)
                .verificationStatus("verified")
                .confidenceScore(78)
                .build());

        // ════════════════════════════════════════════════════════
        // DRIVERS
        // ════════════════════════════════════════════════════════
        Driver baraka = driverRepository.save(Driver.builder()
                .fullName("Baraka Mwinyi")
                .phone("+255784567890")
                .licenseNumber("TZ-DL-2024-0451")
                .vehicleType("motorcycle")
                .homeZone(dodomaCbd)
                .maxCapacityKg(BigDecimal.valueOf(50))
                .maxPackages(10)
                .rating(BigDecimal.valueOf(4.8))
                .totalDeliveries(312)
                .build());

        Driver said = driverRepository.save(Driver.builder()
                .fullName("Said Hamisi")
                .phone("+255756678901")
                .licenseNumber("TZ-DL-2023-1187")
                .vehicleType("van")
                .homeZone(miyujiMkulu)
                .maxCapacityKg(BigDecimal.valueOf(500))
                .maxPackages(50)
                .rating(BigDecimal.valueOf(4.5))
                .totalDeliveries(198)
                .build());

        Driver aminaDriver = driverRepository.save(Driver.builder()
                .fullName("Amina Jongo")
                .phone("+255727789012")
                .vehicleType("bicycle")
                .homeZone(dodomaCbd)
                .maxCapacityKg(BigDecimal.valueOf(20))
                .maxPackages(5)
                .rating(BigDecimal.valueOf(4.9))
                .totalDeliveries(74)
                .build());

        // ════════════════════════════════════════════════════════
        // ORDERS — mix of statuses
        // ════════════════════════════════════════════════════════

        // Today's orders — assigned to routes
        Order orderJuma = orderRepository.save(Order.builder()
                .orderNumber("ORD-2024-0001")
                .customer(juma)
                .deliveryLocation(jumaHome)
                .packageCount(2)
                .totalWeightKg(BigDecimal.valueOf(3.5))
                .packageSize("medium")
                .isFragile(false)
                .requestedDate(LocalDate.now())
                .timeWindowStart(LocalTime.of(10, 0))
                .timeWindowEnd(LocalTime.of(14, 0))
                .priority(2)
                .status("assigned")
                .build());

        Order orderHalima = orderRepository.save(Order.builder()
                .orderNumber("ORD-2024-0002")
                .customer(halima)
                .deliveryLocation(halimaShop)
                .packageCount(1)
                .totalWeightKg(BigDecimal.valueOf(0.5))
                .packageSize("small")
                .isPerishable(true)
                .requestedDate(LocalDate.now())
                .priority(1)
                .status("assigned")
                .build());

        Order orderEmmanuel = orderRepository.save(Order.builder()
                .orderNumber("ORD-2024-0003")
                .customer(emmanuel)
                .deliveryLocation(emmanuelOffice)
                .packageCount(3)
                .totalWeightKg(BigDecimal.valueOf(12.0))
                .packageSize("large")
                .isFragile(true)
                .requestedDate(LocalDate.now())
                .timeWindowStart(LocalTime.of(8, 0))
                .timeWindowEnd(LocalTime.of(12, 0))
                .priority(2)
                .status("assigned")
                .build());

        Order orderFadhili = orderRepository.save(Order.builder()
                .orderNumber("ORD-2024-0004")
                .customer(fadhili)
                .deliveryLocation(fadhiliWarehouse)
                .packageCount(10)
                .totalWeightKg(BigDecimal.valueOf(45.0))
                .packageSize("large")
                .codAmount(BigDecimal.valueOf(120000))
                .requestedDate(LocalDate.now())
                .timeWindowStart(LocalTime.of(7, 0))
                .timeWindowEnd(LocalTime.of(10, 0))
                .priority(1)
                .status("assigned")
                .build());

        Order orderGrace = orderRepository.save(Order.builder()
                .orderNumber("ORD-2024-0005")
                .customer(grace)
                .deliveryLocation(graceHome)
                .packageCount(1)
                .totalWeightKg(BigDecimal.valueOf(1.2))
                .packageSize("small")
                .isFragile(true)
                .requestedDate(LocalDate.now())
                .priority(3)
                .status("assigned")
                .build());

        // Tomorrow's pending orders (not yet assigned)
        orderRepository.save(Order.builder()
                .orderNumber("ORD-2024-0006")
                .customer(rehema)
                .deliveryLocation(rehemaFarm)
                .packageCount(5)
                .totalWeightKg(BigDecimal.valueOf(25.0))
                .packageSize("large")
                .codAmount(BigDecimal.valueOf(45000))
                .requestedDate(LocalDate.now().plusDays(1))
                .priority(3)
                .status("pending")
                .build());

        orderRepository.save(Order.builder()
                .orderNumber("ORD-2024-0007")
                .customer(bakari)
                .deliveryLocation(bakariShop)
                .packageCount(2)
                .totalWeightKg(BigDecimal.valueOf(8.0))
                .packageSize("medium")
                .requestedDate(LocalDate.now().plusDays(1))
                .priority(2)
                .status("pending")
                .build());

        orderRepository.save(Order.builder()
                .orderNumber("ORD-2024-0008")
                .customer(amina)
                .deliveryLocation(aminaHome)
                .packageCount(1)
                .totalWeightKg(BigDecimal.valueOf(2.0))
                .packageSize("small")
                .isPerishable(true)
                .requestedDate(LocalDate.now().plusDays(1))
                .timeWindowStart(LocalTime.of(8, 0))
                .timeWindowEnd(LocalTime.of(12, 0))
                .priority(1)
                .status("pending")
                .build());

        orderRepository.save(Order.builder()
                .orderNumber("ORD-2024-0009")
                .customer(grace)
                .deliveryLocation(graceHome)
                .packageCount(3)
                .totalWeightKg(BigDecimal.valueOf(6.5))
                .packageSize("medium")
                .isFragile(true)
                .requestedDate(LocalDate.now().plusDays(1))
                .priority(2)
                .status("pending")
                .build());

        // ════════════════════════════════════════════════════════
        // DELIVERY ROUTES + ROUTE STOPS
        // ════════════════════════════════════════════════════════

        // Route 1: Baraka on motorcycle — CBD + Viwandani (today, in_progress)
        DeliveryRoute route1 = routeRepository.save(DeliveryRoute.builder()
                .driver(baraka)
                .routeDate(LocalDate.now())
                .totalStops(3)
                .totalDistanceKm(BigDecimal.valueOf(8.5))
                .estimatedDurationMins(55)
                .algorithmVersion("v1.0")
                .status("in_progress")
                .startedAt(LocalDateTime.now().minusHours(1))
                .build());

        // Stop 1 — Halima's shop (delivered)
        stopRepository.save(RouteStop.builder()
                .route(route1)
                .order(orderHalima)
                .sequenceNumber(1)
                .plannedArrival(LocalTime.of(8, 30))
                .actualArrival(LocalDateTime.now().minusMinutes(50))
                .actualDurationMins(5)
                .deliveryLat(-6.1650)
                .deliveryLng(35.7505)
                .status("delivered")
                .recipientName("Halima")
                .build());

        // Stop 2 — Juma's home (delivered)
        stopRepository.save(RouteStop.builder()
                .route(route1)
                .order(orderJuma)
                .sequenceNumber(2)
                .plannedArrival(LocalTime.of(9, 0))
                .actualArrival(LocalDateTime.now().minusMinutes(30))
                .actualDurationMins(8)
                .deliveryLat(-6.1635)
                .deliveryLng(35.7525)
                .status("delivered")
                .recipientName("Juma Abdallah")
                .build());

        // Stop 3 — Fadhili warehouse (pending, next delivery)
        stopRepository.save(RouteStop.builder()
                .route(route1)
                .order(orderFadhili)
                .sequenceNumber(3)
                .plannedArrival(LocalTime.of(9, 30))
                .deliveryLat(-6.1755)
                .deliveryLng(35.7360)
                .status("pending")
                .build());

        // Route 2: Said with van — Miyuji + Kikuyu (today, planned)
        DeliveryRoute route2 = routeRepository.save(DeliveryRoute.builder()
                .driver(said)
                .routeDate(LocalDate.now())
                .totalStops(2)
                .totalDistanceKm(BigDecimal.valueOf(12.3))
                .estimatedDurationMins(70)
                .algorithmVersion("v1.0")
                .status("planned")
                .build());

        // Stop 1 — Emmanuel's office
        stopRepository.save(RouteStop.builder()
                .route(route2)
                .order(orderEmmanuel)
                .sequenceNumber(1)
                .plannedArrival(LocalTime.of(10, 0))
                .deliveryLat(-6.1885)
                .deliveryLng(35.7785)
                .status("pending")
                .build());

        // Stop 2 — Grace's home
        stopRepository.save(RouteStop.builder()
                .route(route2)
                .order(orderGrace)
                .sequenceNumber(2)
                .plannedArrival(LocalTime.of(10, 45))
                .deliveryLat(-6.1525)
                .deliveryLng(35.7660)
                .status("pending")
                .build());

        // Route 3: Completed route from yesterday (Baraka)
        DeliveryRoute route3 = routeRepository.save(DeliveryRoute.builder()
                .driver(baraka)
                .routeDate(LocalDate.now().minusDays(1))
                .totalStops(2)
                .totalDistanceKm(BigDecimal.valueOf(6.0))
                .estimatedDurationMins(40)
                .actualDurationMins(38)
                .algorithmVersion("v1.0")
                .status("completed")
                .startedAt(LocalDateTime.now().minusDays(1).withHour(8).withMinute(0))
                .completedAt(LocalDateTime.now().minusDays(1).withHour(8).withMinute(38))
                .build());

        // Past orders for the completed route
        Order pastOrder1 = orderRepository.save(Order.builder()
                .orderNumber("ORD-2024-0010")
                .customer(bakari)
                .deliveryLocation(bakariShop)
                .packageCount(1)
                .totalWeightKg(BigDecimal.valueOf(3.0))
                .packageSize("medium")
                .requestedDate(LocalDate.now().minusDays(1))
                .priority(2)
                .status("delivered")
                .build());

        Order pastOrder2 = orderRepository.save(Order.builder()
                .orderNumber("ORD-2024-0011")
                .customer(amina)
                .deliveryLocation(aminaHome)
                .packageCount(2)
                .totalWeightKg(BigDecimal.valueOf(4.5))
                .packageSize("medium")
                .isPerishable(true)
                .requestedDate(LocalDate.now().minusDays(1))
                .priority(1)
                .status("delivered")
                .build());

        stopRepository.save(RouteStop.builder()
                .route(route3)
                .order(pastOrder1)
                .sequenceNumber(1)
                .plannedArrival(LocalTime.of(8, 0))
                .actualArrival(LocalDateTime.now().minusDays(1).withHour(8).withMinute(5))
                .actualDurationMins(6)
                .deliveryLat(-6.1365)
                .deliveryLng(35.7188)
                .status("delivered")
                .recipientName("Bakari Moshi")
                .build());

        stopRepository.save(RouteStop.builder()
                .route(route3)
                .order(pastOrder2)
                .sequenceNumber(2)
                .plannedArrival(LocalTime.of(8, 25))
                .actualArrival(LocalDateTime.now().minusDays(1).withHour(8).withMinute(30))
                .actualDurationMins(8)
                .deliveryLat(-6.1115)
                .deliveryLng(35.7675)
                .status("delivered")
                .recipientName("Amina Lugendo")
                .build());

        log.info("Sample data for Dodoma Region initialized successfully!");
        log.info("  6 zones, 11 landmarks, 8 customers, 9 locations");
        log.info("  3 drivers, 11 orders, 3 routes with 7 stops");
    }

    private void seedUsers() {
        if (!userRepository.existsByUsername("admin")) {
            userRepository.save(User.builder()
                    .username("admin")
                    .email("admin@routeoptimizer.com")
                    .fullName("System Administrator")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .enabled(true)
                    .build());
            log.info("Created admin user");
        }
        if (!userRepository.existsByUsername("dispatcher")) {
            userRepository.save(User.builder()
                    .username("dispatcher")
                    .email("dispatcher@routeoptimizer.com")
                    .fullName("Main Dispatcher")
                    .password(passwordEncoder.encode("dispatch123"))
                    .role(Role.DISPATCHER)
                    .enabled(true)
                    .build());
            log.info("Created dispatcher user");
        }
        if (!userRepository.existsByUsername("driver")) {
            userRepository.save(User.builder()
                    .username("driver")
                    .email("driver@routeoptimizer.com")
                    .fullName("Test Driver")
                    .password(passwordEncoder.encode("driver123"))
                    .role(Role.DRIVER)
                    .enabled(true)
                    .build());
            log.info("Created driver user");
        }
    }
}
