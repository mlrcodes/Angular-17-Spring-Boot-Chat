package com.miguel.chatserver.CONTROLLERS;

import com.miguel.chatserver.DTO.AuthLoginRequest;
import com.miguel.chatserver.DTO.AuthLoginResponse;
import com.miguel.chatserver.DTO.AuthRegisterRequest;
import com.miguel.chatserver.DTO.AuthRegisterResponse;
import com.miguel.chatserver.MODELS.Token;
import com.miguel.chatserver.SERVICES.IAuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
  @Autowired
  private IAuthenticationService authenticationService;

  @Autowired
  private HttpServletResponse httpServletResponse;

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<AuthRegisterResponse> register (
    @Valid @RequestBody AuthRegisterRequest request
  ) {
    return ResponseEntity.ok(authenticationService.register(request));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login (
    @Valid @RequestBody AuthLoginRequest request
  ) {
    Token token = authenticationService.login(request);

      ResponseCookie cookie = ResponseCookie.from("token", token.getToken())
        .httpOnly(true)
        .secure(false)
        .path("/")
        .maxAge(token.getExpiresAt().getTime())
        .build();

      httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

      return ResponseEntity.ok(AuthLoginResponse.builder().token(token.getToken()).build());
  }







}
