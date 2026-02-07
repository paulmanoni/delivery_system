package com.example.delivery_system.controller;

import com.example.delivery_system.entity.Driver;
import com.example.delivery_system.service.DeliveryZoneService;
import com.example.delivery_system.service.DriverService;
import com.example.delivery_system.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;
    private final DeliveryZoneService zoneService;
    private final RouteService routeService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("drivers", driverService.findAll());
        model.addAttribute("pageTitle", "Drivers");
        return "drivers/list";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("driver", new Driver());
        model.addAttribute("zones", zoneService.findAllActive());
        model.addAttribute("pageTitle", "New Driver");
        return "drivers/form";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable UUID id, Model model) {
        return driverService.findById(id)
                .map(driver -> {
                    model.addAttribute("driver", driver);
                    model.addAttribute("routes", routeService.findByDriver(id));
                    model.addAttribute("pageTitle", driver.getFullName());
                    return "drivers/view";
                })
                .orElse("redirect:/drivers");
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable UUID id, Model model) {
        return driverService.findById(id)
                .map(driver -> {
                    model.addAttribute("driver", driver);
                    model.addAttribute("zones", zoneService.findAllActive());
                    model.addAttribute("pageTitle", "Edit Driver");
                    return "drivers/form";
                })
                .orElse("redirect:/drivers");
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Driver driver, RedirectAttributes redirectAttributes) {
        driverService.save(driver);
        redirectAttributes.addFlashAttribute("success", "Driver saved successfully");
        return "redirect:/drivers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        driverService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Driver deleted successfully");
        return "redirect:/drivers";
    }

    @PostMapping("/{id}/toggle")
    public String toggleActive(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        driverService.toggleActive(id);
        redirectAttributes.addFlashAttribute("success", "Driver status updated");
        return "redirect:/drivers";
    }
}
