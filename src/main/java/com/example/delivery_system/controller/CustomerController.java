package com.example.delivery_system.controller;

import com.example.delivery_system.entity.Customer;
import com.example.delivery_system.entity.CustomerLocation;
import com.example.delivery_system.service.CustomerService;
import com.example.delivery_system.service.DeliveryZoneService;
import com.example.delivery_system.service.LandmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final DeliveryZoneService zoneService;
    private final LandmarkService landmarkService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("pageTitle", "Customers");
        return "customers/list";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("pageTitle", "New Customer");
        return "customers/form";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable UUID id, Model model) {
        return customerService.findByIdWithLocations(id)
                .map(customer -> {
                    model.addAttribute("customer", customer);
                    model.addAttribute("locations", customerService.findLocationsByCustomer(id));
                    model.addAttribute("pageTitle", customer.getFullName());
                    return "customers/view";
                })
                .orElse("redirect:/customers");
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable UUID id, Model model) {
        return customerService.findById(id)
                .map(customer -> {
                    model.addAttribute("customer", customer);
                    model.addAttribute("pageTitle", "Edit Customer");
                    return "customers/form";
                })
                .orElse("redirect:/customers");
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Customer customer, RedirectAttributes redirectAttributes) {
        customerService.save(customer);
        redirectAttributes.addFlashAttribute("success", "Customer saved successfully");
        return "redirect:/customers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        customerService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Customer deleted successfully");
        return "redirect:/customers";
    }

    @GetMapping("/{customerId}/locations/new")
    public String createLocation(@PathVariable UUID customerId, Model model) {
        return customerService.findById(customerId)
                .map(customer -> {
                    CustomerLocation location = new CustomerLocation();
                    location.setCustomer(customer);
                    model.addAttribute("location", location);
                    model.addAttribute("customer", customer);
                    model.addAttribute("zones", zoneService.findAllActive());
                    model.addAttribute("landmarks", landmarkService.findAll());
                    model.addAttribute("pageTitle", "New Location");
                    return "customers/location-form";
                })
                .orElse("redirect:/customers");
    }

    @PostMapping("/{customerId}/locations/save")
    public String saveLocation(@PathVariable UUID customerId, @ModelAttribute CustomerLocation location,
                               RedirectAttributes redirectAttributes) {
        customerService.findById(customerId).ifPresent(location::setCustomer);
        customerService.saveLocation(location);
        redirectAttributes.addFlashAttribute("success", "Location saved successfully");
        return "redirect:/customers/" + customerId;
    }
}
