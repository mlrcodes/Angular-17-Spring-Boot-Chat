package com.miguel.chatserver.CONTROLLERS;

import com.miguel.chatserver.DTO.AuthLoginRequest;
import com.miguel.chatserver.DTO.AuthLoginResponse;
import com.miguel.chatserver.DTO.AuthRegisterRequest;
import com.miguel.chatserver.DTO.AuthRegisterResponse;
import com.miguel.chatserver.SERVICES.IAuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {

  @Autowired
  private IAuthenticationService authenticationService;

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<AuthRegisterResponse> register (
    @Valid @RequestBody AuthRegisterRequest request
  ) {
    return ResponseEntity.ok(authenticationService.register(request));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthLoginResponse> login (
    @Valid @RequestBody AuthLoginRequest request
  ) {
    return ResponseEntity.ok(authenticationService.login(request));
  }

}
