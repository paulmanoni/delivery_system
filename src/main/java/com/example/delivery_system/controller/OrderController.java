package com.example.delivery_system.controller;

import com.example.delivery_system.entity.Customer;
import com.example.delivery_system.entity.CustomerLocation;
import com.example.delivery_system.entity.Order;
import com.example.delivery_system.service.CustomerService;
import com.example.delivery_system.service.OrderService;
import tools.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;

    @GetMapping
    public String list(@RequestParam(required = false) String status, Model model) {
        if (status != null && !status.isEmpty()) {
            model.addAttribute("orders", orderService.findByStatus(status));
            model.addAttribute("currentStatus", status);
        } else {
            model.addAttribute("orders", orderService.findAll());
        }
        model.addAttribute("pageTitle", "Orders");
        return "orders/list";
    }

    @GetMapping("/new")
    public String create(Model model) {
        Order order = new Order();
        order.setOrderNumber(orderService.generateOrderNumber());
        model.addAttribute("order", order);
        model.addAttribute("customers", customerService.findAll());
        addLocationData(model);
        model.addAttribute("pageTitle", "New Order");
        return "orders/form";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable UUID id, Model model) {
        return orderService.findById(id)
                .map(order -> {
                    model.addAttribute("order", order);
                    model.addAttribute("pageTitle", "Order " + order.getOrderNumber());
                    return "orders/view";
                })
                .orElse("redirect:/orders");
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable UUID id, Model model) {
        return orderService.findById(id)
                .map(order -> {
                    model.addAttribute("order", order);
                    model.addAttribute("customers", customerService.findAll());
                    addLocationData(model);
                    model.addAttribute("pageTitle", "Edit Order");
                    return "orders/form";
                })
                .orElse("redirect:/orders");
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Order order, RedirectAttributes redirectAttributes) {
        orderService.save(order);
        redirectAttributes.addFlashAttribute("success", "Order saved successfully");
        return "redirect:/orders";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable UUID id, @RequestParam String status,
                               RedirectAttributes redirectAttributes) {
        orderService.updateStatus(id, status);
        redirectAttributes.addFlashAttribute("success", "Order status updated");
        return "redirect:/orders/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        orderService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Order deleted successfully");
        return "redirect:/orders";
    }

    private void addLocationData(Model model) {
        List<Customer> customers = customerService.findAll();
        Map<String, List<Map<String, Object>>> locationMap = new LinkedHashMap<>();
        for (Customer c : customers) {
            List<Map<String, Object>> locs = customerService.findLocationsByCustomer(c.getCustomerId()).stream()
                    .map(loc -> {
                        Map<String, Object> m = new LinkedHashMap<>();
                        m.put("id", loc.getLocationId().toString());
                        m.put("name", loc.getLocationName());
                        m.put("description", loc.getLocationDescription());
                        m.put("lat", loc.getLatitude());
                        m.put("lng", loc.getLongitude());
                        m.put("isDefault", Boolean.TRUE.equals(loc.getIsDefault()));
                        return m;
                    })
                    .collect(Collectors.toList());
            locationMap.put(c.getCustomerId().toString(), locs);
        }
        try {
            model.addAttribute("allLocationsJson", new ObjectMapper().writeValueAsString(locationMap));
        } catch (Exception e) {
            model.addAttribute("allLocationsJson", "{}");
        }
    }
}
