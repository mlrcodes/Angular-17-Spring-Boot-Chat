package com.miguel.chatserver.CONFIGS;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
@AllArgsConstructor
public class CorsConfig {

  @Bean
  public CorsFilter corsFilter() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    final CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.setAllowedOrigins(
      Collections.singletonList("http://localhost:4200")
    );
    config.setAllowedHeaders(
      Arrays.asList(
        ORIGIN,
        CONTENT_TYPE,
        ACCEPT,
        AUTHORIZATION
      )
    );
    config.setAllowedMethods(
      Arrays.asList(
        "GET",
        "POST",
        "PUT",
        "DELETE",
        "PATCH"
      )
    );
    config.addExposedHeader(HttpHeaders.SET_COOKIE);
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

}
