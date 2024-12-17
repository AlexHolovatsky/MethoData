package org.example.metho.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class CorsConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // Вимкнення CSRF для POST-запитів
                .authorizeHttpRequests(auth -> auth
                        .antMatchers("/api/evaluate").permitAll() // Дозволяємо доступ до вашого ендпоінту
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
