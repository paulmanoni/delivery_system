package com.example.delivery_system.controller;

import com.example.delivery_system.dto.RegistrationDto;
import com.example.delivery_system.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("registration", new RegistrationDto());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registration") RegistrationDto dto,
                           BindingResult result, Model model) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.registration", "Passwords do not match");
        }
        if (userService.existsByUsername(dto.getUsername())) {
            result.rejectValue("username", "error.registration", "Username is already taken");
        }
        if (userService.existsByEmail(dto.getEmail())) {
            result.rejectValue("email", "error.registration", "Email is already registered");
        }
        if (result.hasErrors()) {
            return "auth/register";
        }
        userService.register(dto);
        return "redirect:/login?registered";
    }
}
