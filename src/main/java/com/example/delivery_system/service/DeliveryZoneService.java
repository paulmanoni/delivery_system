package com.example.delivery_system.service;

import com.example.delivery_system.entity.DeliveryZone;
import com.example.delivery_system.repository.DeliveryZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryZoneService {

    private final DeliveryZoneRepository zoneRepository;

    public List<DeliveryZone> findAll() {
        return zoneRepository.findAll();
    }

    public List<DeliveryZone> findAllActive() {
        return zoneRepository.findAllActiveOrderByName();
    }

    public Optional<DeliveryZone> findById(UUID id) {
        return zoneRepository.findById(id);
    }

    public List<DeliveryZone> findRootZones() {
        return zoneRepository.findByParentZoneIsNull();
    }

    public List<DeliveryZone> findChildZones(DeliveryZone parent) {
        return zoneRepository.findByParentZone(parent);
    }

    public DeliveryZone save(DeliveryZone zone) {
        return zoneRepository.save(zone);
    }

    public void delete(UUID id) {
        zoneRepository.deleteById(id);
    }

    public void toggleActive(UUID id) {
        zoneRepository.findById(id).ifPresent(zone -> {
            zone.setIsActive(!zone.getIsActive());
            zoneRepository.save(zone);
        });
    }
}
