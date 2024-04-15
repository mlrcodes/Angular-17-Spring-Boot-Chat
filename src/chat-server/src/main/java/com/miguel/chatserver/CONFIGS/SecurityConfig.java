package com.miguel.chatserver.CONFIGS;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@NoArgsConstructor
@AllArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

  @Autowired
  private JWTAuthenticationFilter jwtAuthenticationFilter;

  @Autowired
  private AuthenticationProvider authProvider;

  @Autowired
  private UserDetailsServiceImp userDetailsServiceImp;
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .cors(withDefaults())
      .csrf(config -> config.disable())
      .formLogin(form -> form.disable())
      .authorizeHttpRequests(req ->
        req.requestMatchers(
          "api/auth/**",
            "/api/auth/**",
            "/auth/**",
            "auth/**",
            "api/auth/register",
            "/api/auth/register",
            "/auth/register",
            "/auth/login",
            "api/auth/login",
            "/api/auth/login",
            "/auth/login",
            "auth/login"
          ).permitAll()
            .anyRequest().permitAll()
      )
      .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
      .authenticationProvider(authProvider)
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
    ;
    return http.build();
  }
}

