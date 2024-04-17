package com.miguel.chatserver.CONTROLLERS;

import com.miguel.chatserver.DTO.AuthLoginRequest;
import com.miguel.chatserver.DTO.AuthLoginResponse;
import com.miguel.chatserver.DTO.AuthRegisterRequest;
import com.miguel.chatserver.DTO.AuthRegisterResponse;
import com.miguel.chatserver.SERVICES.IAuthenticationService;
import com.miguel.chatserver.SERVICES.IJWTService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
  @Autowired
  private IAuthenticationService authenticationService;

  @Autowired
  private IJWTService jwtService;

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<AuthRegisterResponse> register (
    @Valid @RequestBody AuthRegisterRequest request
  ) {
    return ResponseEntity.ok(authenticationService.register(request));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthLoginResponse> login (
    @Valid @RequestBody AuthLoginRequest request,
    HttpServletResponse response
  ) {
      return ResponseEntity.ok(authenticationService.login(request));
  }







}
