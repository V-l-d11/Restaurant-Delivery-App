package com.oder.food.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
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
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
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
                         "http://localhost:4200",
                         "http://localhost:8080"
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


@Bean OpenAPI openAPI(){
 return new OpenAPI()
         .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
         .components(new Components().addSecuritySchemes("bearerAuth",new SecurityScheme().type(SecurityScheme.Type.HTTP)
                         .scheme("bearer")
                         .bearerFormat("JWT"))

         ).info(new Info()
                 .title("Resturant List API")
                 .description("Sp app")
                 .version("1.0"));
}

}
