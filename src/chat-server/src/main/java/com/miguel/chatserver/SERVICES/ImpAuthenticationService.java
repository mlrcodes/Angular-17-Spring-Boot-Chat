package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.AuthLoginRequest;
import com.miguel.chatserver.DTO.AuthLoginResponse;
import com.miguel.chatserver.DTO.AuthRegisterRequest;
import com.miguel.chatserver.DTO.AuthRegisterResponse;
import com.miguel.chatserver.EXCEPTIONS.ExceptionObjectAlreadyExists;
import com.miguel.chatserver.EXCEPTIONS.ExceptionObjectNotFound;
import com.miguel.chatserver.MODELS.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImpAuthenticationService implements IAuthenticationService{

  @Value("${application.mailing.frontend.activation-url}")
  private String activationUrl;

  @Autowired
  private IUserService userService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private IJWTService jwtService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public AuthRegisterResponse register(AuthRegisterRequest request) {
    String phoneNumber = request.getPhoneNumber();
    User userByPhoneNumber = this.userService.findByPhoneNumber(phoneNumber);
    if (Objects.nonNull(userByPhoneNumber)) {
      throw new ExceptionObjectAlreadyExists("Phone Number Already In Use");
    }

    String email = request.getEmail();
    User userByEmail = this.userService.findByEmail(email);
    if (Objects.nonNull(userByEmail)) {
      throw new ExceptionObjectAlreadyExists("Phone Number Already In Use");
    }

    User user = this.userService.createUserFromRegisterRequest(request);
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    try {
      this.userService.registerUser(user);
      return AuthRegisterResponse.builder()
        .success(true)
        .message("User registered successfully")
        .phoneNumber(user.getPhoneNumber())
        .build();
    } catch (Exception ex) {
      throw ex;
    }
  }

  @Override
  public AuthLoginResponse login(AuthLoginRequest request) {

    Authentication auth = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.getPhoneNumber(),
        request.getPassword()
      )
    );
    var claims = new HashMap<String, Object>();
    var user = ((User) auth.getPrincipal());
    var jwt = jwtService.generateToken(claims, user);

    return AuthLoginResponse
      .builder()
        .token(jwt)
      .build();
  }
}
