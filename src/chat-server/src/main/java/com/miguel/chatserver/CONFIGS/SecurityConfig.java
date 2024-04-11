package com.miguel.chatserver.CONFIGS;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.AuthProvider;


@Configuration
@EnableWebSecurity
@NoArgsConstructor
@AllArgsConstructor
public class SecurityConfig {


  @Autowired
  private JWTAuthenticationFilter jwtAuthenticationFilter;

  @Autowired
  private DaoAuthenticationProvider authProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
  {
    return http
      .csrf(csrf ->
        csrf.disable()
      )
      .authorizeHttpRequests(authRequest ->
        authRequest
          .requestMatchers("/auth/**").permitAll()
          .anyRequest().authenticated()
      )
      .sessionManagement(sessionManager->
        sessionManager
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authenticationProvider(authProvider)
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
      .build();
  }

}
