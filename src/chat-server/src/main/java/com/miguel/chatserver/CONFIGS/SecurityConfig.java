package com.miguel.chatserver.CONFIGS;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

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
      .authorizeHttpRequests(req ->
        req.requestMatchers(
          "/api/auth/login",
          "/api/auth/register"
          ).permitAll()
            .anyRequest().permitAll()
      )
      .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
      .authenticationProvider(authProvider)
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
    ;
    return http.build();
  }

  @Bean
  public void configure(WebSecurity web) {
    web.ignoring().requestMatchers(HttpMethod.OPTIONS, "/api/auth/login");
  }

}

