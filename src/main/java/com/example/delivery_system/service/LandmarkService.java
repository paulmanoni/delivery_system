package com.example.delivery_system.service;

import com.example.delivery_system.entity.Landmark;
import com.example.delivery_system.repository.LandmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class LandmarkService {

    private final LandmarkRepository landmarkRepository;

    public List<Landmark> findAll() {
        return landmarkRepository.findAll();
    }

    public Optional<Landmark> findById(UUID id) {
        return landmarkRepository.findById(id);
    }

    public List<Landmark> findByZone(UUID zoneId) {
        return landmarkRepository.findByZoneZoneId(zoneId);
    }

    public List<Landmark> findByType(String type) {
        return landmarkRepository.findByLandmarkType(type);
    }

    public Landmark save(Landmark landmark) {
        return landmarkRepository.save(landmark);
    }

    public void delete(UUID id) {
        landmarkRepository.deleteById(id);
    }
}
