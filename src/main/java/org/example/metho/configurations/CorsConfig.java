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
        HttpSecurity httpSecurity = http.csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .antMatchers("/api/evaluate").permitAll()
                        .antMatchers("/").permitAll()
                        .antMatchers("classpath:/static/").permitAll()
                        .antMatchers("/static/**").permitAll()
                        .antMatchers("/api/**").permitAll()
                        .antMatchers("https://unpkg.com/leaflet@1.9.4/dist/leaflet.js").permitAll()
                        .antMatchers("https://cdn.jsdelivr.net/npm/chart.js").permitAll()
                        .antMatchers("https://unpkg.com/leaflet@1.9.4/dist/leaflet.css").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }


}
