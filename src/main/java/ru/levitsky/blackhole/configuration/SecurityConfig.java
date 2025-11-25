package ru.levitsky.blackhole.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Разрешаем все локальные запросы без аутентификации
                .authorizeHttpRequests(auth -> auth.requestMatchers("/**").permitAll().anyRequest().permitAll())
                // Отключаем CSRF, чтобы не мешал при POST-запросах с Postman
                .csrf(csrf -> csrf.disable())
                // Можно отключить форму логина
                .httpBasic(Customizer.withDefaults())
                .formLogin(login -> login.disable());

        return http.build();
    }
}
