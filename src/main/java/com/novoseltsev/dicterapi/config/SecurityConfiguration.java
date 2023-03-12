package com.novoseltsev.dicterapi.config;

import com.novoseltsev.dicterapi.domain.role.UserRole;
import com.novoseltsev.dicterapi.security.jwt.JwtConfigurer;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    private final JwtConfigurer jwtConfigurer;

    public SecurityConfiguration(JwtConfigurer jwtConfigurer) {
        this.jwtConfigurer = jwtConfigurer;
    }

    private static final List<String> ADMIN_ENDPOINTS = List.of(
        "/users"
    );
    private static final List<String> OPEN_ENDPOINTS = List.of(
        "/users/registration",
        "/auth",

        "/v3/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/swagger-resources/**",
        "/webjars/**"
    );

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .httpBasic()
            .disable()
            .csrf()
            .disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "**").permitAll()
            .antMatchers(OPEN_ENDPOINTS.toArray(String[]::new)).permitAll()
            .antMatchers(ADMIN_ENDPOINTS.toArray(String[]::new)).hasAuthority(UserRole.ADMIN.name())
            .anyRequest().authenticated()
            .and()
            .apply(jwtConfigurer);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
