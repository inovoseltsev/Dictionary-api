package com.novoseltsev.dictionaryapi.config;

import com.novoseltsev.dictionaryapi.security.jwt.JwtConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] USER_ENDPOINTS = new String[]{"/users"};
    private static final String[] ADMIN_ENDPOINTS = new String[]{};
    private static final String[] FREE_ENDPOINTS = new String[]{
            "/users/registration", "/auth"
    };

    @Autowired
    private JwtConfigurer jwtConfigurer;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .disable()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(USER_ENDPOINTS).hasAnyAuthority("USER", "ADMIN")
                .antMatchers(ADMIN_ENDPOINTS).hasAuthority("ADMIN")
                .antMatchers(FREE_ENDPOINTS).permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(jwtConfigurer);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
