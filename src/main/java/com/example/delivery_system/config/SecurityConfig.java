package com.example.delivery_system.config;

import com.example.delivery_system.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/landing", "/register", "/login", "/css/**", "/js/**",
                        "/webjars/**", "/favicon.ico", "/error").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/orders/new", "/orders/save", "/orders/*/edit", "/orders/*/delete",
                        "/orders/*/status").hasAnyRole("ADMIN", "DISPATCHER")
                .requestMatchers("/customers/**").hasAnyRole("ADMIN", "DISPATCHER")
                .requestMatchers("/drivers/**").hasAnyRole("ADMIN", "DISPATCHER")
                .requestMatchers("/zones/**").hasAnyRole("ADMIN", "DISPATCHER")
                .requestMatchers("/routes/new", "/routes/save", "/routes/*/edit", "/routes/*/delete",
                        "/routes/*/start", "/routes/*/complete", "/routes/stops/**").hasAnyRole("ADMIN", "DISPATCHER")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/landing")
                .permitAll()
            )
            .rememberMe(remember -> remember
                .userDetailsService(userDetailsService)
            );

        return http.build();
    }
}
