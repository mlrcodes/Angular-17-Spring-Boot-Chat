package com.miguel.chatserver.CONFIGS;

import com.miguel.chatserver.SERVICES.IJWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;


@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private IJWTService jwtService;

  @Autowired
  private UserDetailsServiceImp userDetailsService;

  @Override
  protected void doFilterInternal(
    @NonNull HttpServletRequest request,
    @NonNull HttpServletResponse response,
    @NonNull FilterChain filterChain
  ) throws ServletException, IOException {

    if (request.getServletPath().contains("/auth")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = null;
    String phoneNumber = null;

    if(request.getCookies() != null){
      for(Cookie cookie: request.getCookies()){
        if(cookie.getName().equals("token")){
          token = cookie.getValue();
        }
      }
    }

    if(Objects.isNull(token)){
      filterChain.doFilter(request, response);
      return;
    }

    phoneNumber = jwtService.getPhoneNumberFromToken(token);

    if(Objects.nonNull(phoneNumber)){
      UserDetails userDetails = userDetailsService.loadUserByUsername(phoneNumber);
      if(jwtService.isTokenValid(token, userDetails)){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }

    }

    filterChain.doFilter(request, response);
  }
}
