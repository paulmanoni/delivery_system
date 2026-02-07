package com.example.delivery_system.service;

import com.example.delivery_system.entity.Customer;
import com.example.delivery_system.entity.CustomerLocation;
import com.example.delivery_system.repository.CustomerLocationRepository;
import com.example.delivery_system.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerLocationRepository locationRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(UUID id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> findByIdWithLocations(UUID id) {
        return customerRepository.findByIdWithLocations(id);
    }

    public List<Customer> searchByName(String name) {
        return customerRepository.searchByName(name);
    }

    public Customer save(Customer customer) {
        customer.setUpdatedAt(LocalDateTime.now());
        return customerRepository.save(customer);
    }

    public void delete(UUID id) {
        customerRepository.deleteById(id);
    }

    public List<CustomerLocation> findLocationsByCustomer(UUID customerId) {
        return locationRepository.findActiveByCustomer(customerId);
    }

    public CustomerLocation saveLocation(CustomerLocation location) {
        location.setUpdatedAt(LocalDateTime.now());
        return locationRepository.save(location);
    }

    public void deleteLocation(UUID locationId) {
        locationRepository.deleteById(locationId);
    }
}
