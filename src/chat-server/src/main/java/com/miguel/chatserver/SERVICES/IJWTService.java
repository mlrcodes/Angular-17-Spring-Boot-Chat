package com.miguel.chatserver.SERVICES;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface IJWTService {

  public String getTokenFromRequestHeaders(HttpServletRequest request);

  public String generateToken(UserDetails user);

  public String generateToken(Map<String, Object> claims, UserDetails user);


  public String getPhoneNumberFromToken(String token);


  public Date getTokenExpiration(String token);

  public boolean isTokenValid(String token, UserDetails userDetails);

  public <T> T getClaim(String token, Function<Claims,T> claimsResolver);

}
