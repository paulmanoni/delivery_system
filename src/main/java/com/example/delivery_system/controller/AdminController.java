package com.example.delivery_system.controller;

import com.example.delivery_system.entity.Role;
import com.example.delivery_system.entity.User;
import com.example.delivery_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("pageTitle", "User Management");
        return "admin/users/list";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        model.addAttribute("pageTitle", "New User");
        return "admin/users/form";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable UUID id, Model model) {
        return userService.findById(id)
                .map(user -> {
                    user.setPassword(null);
                    model.addAttribute("user", user);
                    model.addAttribute("roles", Role.values());
                    model.addAttribute("pageTitle", "Edit User");
                    return "admin/users/form";
                })
                .orElse("redirect:/admin/users");
    }

    @PostMapping("/save")
    public String save(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        userService.adminSave(user);
        redirectAttributes.addFlashAttribute("success", "User saved successfully");
        return "redirect:/admin/users";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        userService.delete(id);
        redirectAttributes.addFlashAttribute("success", "User deleted successfully");
        return "redirect:/admin/users";
    }
}
