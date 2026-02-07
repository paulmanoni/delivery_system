package com.example.delivery_system.controller;

import com.example.delivery_system.entity.DeliveryRoute;
import com.example.delivery_system.service.DriverService;
import com.example.delivery_system.service.OrderService;
import com.example.delivery_system.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.UUID;

@Controller
@RequestMapping("/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;
    private final DriverService driverService;
    private final OrderService orderService;

    @GetMapping
    public String list(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                       Model model) {
        if (date != null) {
            model.addAttribute("routes", routeService.findByDate(date));
            model.addAttribute("selectedDate", date);
        } else {
            model.addAttribute("routes", routeService.findAll());
        }
        model.addAttribute("pageTitle", "Delivery Routes");
        return "routes/list";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("route", new DeliveryRoute());
        model.addAttribute("drivers", driverService.findAllActive());
        model.addAttribute("pendingOrders", orderService.findUndelivered());
        model.addAttribute("pageTitle", "New Route");
        return "routes/form";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable UUID id, Model model) {
        DeliveryRoute route = routeService.findByIdWithStops(id);
        if (route != null) {
            model.addAttribute("route", route);
            model.addAttribute("stops", routeService.findStopsByRoute(id));
            model.addAttribute("pageTitle", "Route Details");
            return "routes/view";
        }
        return "redirect:/routes";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable UUID id, Model model) {
        return routeService.findById(id)
                .map(route -> {
                    model.addAttribute("route", route);
                    model.addAttribute("drivers", driverService.findAllActive());
                    model.addAttribute("pageTitle", "Edit Route");
                    return "routes/form";
                })
                .orElse("redirect:/routes");
    }

    @PostMapping("/save")
    public String save(@ModelAttribute DeliveryRoute route, RedirectAttributes redirectAttributes) {
        routeService.save(route);
        redirectAttributes.addFlashAttribute("success", "Route saved successfully");
        return "redirect:/routes";
    }

    @PostMapping("/{id}/start")
    public String startRoute(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        routeService.startRoute(id);
        redirectAttributes.addFlashAttribute("success", "Route started");
        return "redirect:/routes/" + id;
    }

    @PostMapping("/{id}/complete")
    public String completeRoute(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        routeService.completeRoute(id);
        redirectAttributes.addFlashAttribute("success", "Route completed");
        return "redirect:/routes/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        routeService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Route deleted successfully");
        return "redirect:/routes";
    }

    @PostMapping("/stops/{stopId}/status")
    public String updateStopStatus(@PathVariable UUID stopId, @RequestParam String status,
                                   @RequestParam(required = false) String failureReason,
                                   @RequestParam UUID routeId, RedirectAttributes redirectAttributes) {
        routeService.updateStopStatus(stopId, status, failureReason);
        redirectAttributes.addFlashAttribute("success", "Stop status updated");
        return "redirect:/routes/" + routeId;
    }
}
