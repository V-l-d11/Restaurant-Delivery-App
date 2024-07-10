package com.oder.food.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.security.Security;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class AppConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/admin/**").hasAnyRole("RESTAURANT_OWNER", "ROLE_ADMIN")
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf()
                .disable()
                .cors()
                .configurationSource(corsConfigurationSource())
                .and()
                .build();

    }

    private CorsConfigurationSource corsConfigurationSource(){
         return new CorsConfigurationSource() {
             @Override
             public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                 CorsConfiguration cfg=new CorsConfiguration();
                 cfg.setAllowedOrigins(Arrays.asList(
                         "http://localhost:4200"
                 ));
                 cfg.setAllowedMethods(Collections.singletonList("*"));
                 cfg.setAllowCredentials(true);
                 cfg.setAllowedHeaders(Collections.singletonList("*"));
                 cfg.setExposedHeaders(Arrays.asList("Authorization"));
                 cfg.setMaxAge(3600L);
                 return cfg;
             }
         };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
