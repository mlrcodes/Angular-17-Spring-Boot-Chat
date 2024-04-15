package com.miguel.chatserver.CONFIGS;

import com.miguel.chatserver.REPOSITORIES.IUserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.http.HttpHeaders.*;

@Configuration
@NoArgsConstructor
@AllArgsConstructor
public class AppConfig {

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private UserDetailsServiceImp userDetailsService;


  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public AuthenticationProvider authenticationProvider()
  {
    DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(encoder());
    return authenticationProvider;
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

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
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }
}
