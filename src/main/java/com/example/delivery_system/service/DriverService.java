package com.example.delivery_system.service;

import com.example.delivery_system.entity.Driver;
import com.example.delivery_system.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DriverService {

    private final DriverRepository driverRepository;

    public List<Driver> findAll() {
        return driverRepository.findAll();
    }

    public List<Driver> findAllActive() {
        return driverRepository.findByIsActiveTrue();
    }

    public Optional<Driver> findById(UUID id) {
        return driverRepository.findById(id);
    }

    public List<Driver> findByVehicleType(String type) {
        return driverRepository.findByVehicleType(type);
    }

    public List<Driver> findByHomeZone(UUID zoneId) {
        return driverRepository.findActiveByHomeZone(zoneId);
    }

    public List<Driver> findTopRated() {
        return driverRepository.findTopRatedDrivers();
    }

    public Driver save(Driver driver) {
        return driverRepository.save(driver);
    }

    public void delete(UUID id) {
        driverRepository.deleteById(id);
    }

    public void toggleActive(UUID id) {
        driverRepository.findById(id).ifPresent(driver -> {
            driver.setIsActive(!driver.getIsActive());
            driverRepository.save(driver);
        });
    }
}
