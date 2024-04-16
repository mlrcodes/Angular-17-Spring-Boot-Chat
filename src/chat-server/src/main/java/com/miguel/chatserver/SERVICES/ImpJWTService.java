package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.MODELS.Token;
import com.miguel.chatserver.REPOSITORIES.ITokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Service
public class ImpJWTService implements IJWTService {

  @Value("${application.security.jwt.secret-key}")
  private String secretKey;

  @Value("${application.security.jwt.expiration}")
  private Long jwtExpiration;

  @Autowired
  private ITokenRepository tokenRepository;


  @Override
  public Token saveToken(Token token) {
    return this.tokenRepository.save(token);
  }

  @Override
  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  public String generateToken(
    Map<String,Object> claims,
    UserDetails userDetails
  ) {
    return buildToken(claims, userDetails, jwtExpiration);
  }

  private String buildToken(
    Map<String,Object> extraClaims,
    UserDetails userDetails,
    Long jwtExpiration
  ) {
    var authorities = userDetails.getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority)
      .toList();
    return Jwts
      .builder()
      .setClaims(extraClaims)
      .setSubject(userDetails.getUsername())
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
      .claim("authorities", authorities)
      .signWith(getSignInKey())
      .compact();
  }


  @Override
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String phoneNumber = getPhoneNumberFromToken(token);
    return (phoneNumber.equals(userDetails.getUsername())&& !isTokenExpired(token));
  }

  @Override
  public String getPhoneNumberFromToken(String token) {
    return getClaim(token, Claims::getSubject);
  }

  @Override
  public Date getTokenExpiration(String token) {
    return getClaim(token, Claims::getExpiration);
  }

  @Override
  public <T> T getClaim(String token, Function<Claims,T> claimsResolver) {
    final Claims claims = getAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaims(String token) {
    return Jwts
      .parserBuilder()
      .setSigningKey(getKey())
      .build()
      .parseClaimsJws(token)
      .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  private boolean isTokenExpired(String token) {
    return getExpiration(token).before(new Date());
  }

  private Date getExpiration(String token) {
    return getClaim(token, Claims::getExpiration);
  }

  private Key getKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

}
