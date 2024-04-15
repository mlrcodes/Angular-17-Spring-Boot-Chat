package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.MODELS.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public interface IJWTService {

  public Token saveToken(Token token);
  public String generateToken(UserDetails user);

  public String generateToken(Map<String, Object> claims, UserDetails user);


  public String getPhoneNumberFromToken(String token);

  public boolean isTokenValid(String token, UserDetails userDetails);


  public <T> T getClaim(String token, Function<Claims,T> claimsResolver);

}
