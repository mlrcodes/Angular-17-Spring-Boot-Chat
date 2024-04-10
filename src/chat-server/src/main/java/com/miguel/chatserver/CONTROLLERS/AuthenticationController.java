package com.miguel.chatserver.CONTROLLERS;

import com.miguel.chatserver.DTO.AuthRegisterRequest;
import com.miguel.chatserver.DTO.AuthRegisterResponse;
import com.miguel.chatserver.SERVICES.IAuthenticationService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  @Autowired
  private IAuthenticationService authenticationService;

  @PostMapping("/register")
  public ResponseEntity<AuthRegisterResponse> register(
    @Valid @RequestBody AuthRegisterRequest request
  ) {
    return ResponseEntity.ok(authenticationService.register(request));
  }

}
