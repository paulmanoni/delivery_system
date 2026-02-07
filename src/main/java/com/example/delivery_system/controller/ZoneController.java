package com.example.delivery_system.controller;

import com.example.delivery_system.entity.DeliveryZone;
import com.example.delivery_system.service.DeliveryZoneService;
import com.example.delivery_system.service.LandmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final DeliveryZoneService zoneService;
    private final LandmarkService landmarkService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("zones", zoneService.findAll());
        model.addAttribute("pageTitle", "Delivery Zones");
        return "zones/list";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("zone", new DeliveryZone());
        model.addAttribute("parentZones", zoneService.findAllActive());
        model.addAttribute("pageTitle", "New Zone");
        return "zones/form";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable UUID id, Model model) {
        return zoneService.findById(id)
                .map(zone -> {
                    model.addAttribute("zone", zone);
                    model.addAttribute("landmarks", landmarkService.findByZone(id));
                    model.addAttribute("pageTitle", zone.getZoneName());
                    return "zones/view";
                })
                .orElse("redirect:/zones");
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable UUID id, Model model) {
        return zoneService.findById(id)
                .map(zone -> {
                    model.addAttribute("zone", zone);
                    model.addAttribute("parentZones", zoneService.findAllActive());
                    model.addAttribute("pageTitle", "Edit Zone");
                    return "zones/form";
                })
                .orElse("redirect:/zones");
    }

    @PostMapping("/save")
    public String save(@ModelAttribute DeliveryZone zone, RedirectAttributes redirectAttributes) {
        zoneService.save(zone);
        redirectAttributes.addFlashAttribute("success", "Zone saved successfully");
        return "redirect:/zones";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        zoneService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Zone deleted successfully");
        return "redirect:/zones";
    }

    @PostMapping("/{id}/toggle")
    public String toggleActive(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        zoneService.toggleActive(id);
        redirectAttributes.addFlashAttribute("success", "Zone status updated");
        return "redirect:/zones";
    }
}
