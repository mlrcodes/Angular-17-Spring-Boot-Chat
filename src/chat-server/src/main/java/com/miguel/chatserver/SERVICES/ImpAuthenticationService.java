package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.AuthLoginRequest;
import com.miguel.chatserver.DTO.AuthLoginResponse;
import com.miguel.chatserver.DTO.AuthRegisterRequest;
import com.miguel.chatserver.DTO.AuthRegisterResponse;
import com.miguel.chatserver.EXCEPTIONS.ExceptionObjectAlreadyExists;
import com.miguel.chatserver.EXCEPTIONS.ExceptionObjectNotFound;
import com.miguel.chatserver.MAPPERS.IUsersMapper;
import com.miguel.chatserver.MODELS.User;
import com.miguel.chatserver.MODELS.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImpAuthenticationService implements IAuthenticationService{

  @Value("${application.mailing.frontend.activation-url}")
  private String activationUrl;

  @Autowired
  private IUsersService userService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private IJWTService jwtService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private IUsersMapper usersMapper;

  @Override
  public AuthRegisterResponse register(AuthRegisterRequest request) {
    String phoneNumber = request.getPhoneNumber();
    User userByPhoneNumber = this.userService.findByPhoneNumber(phoneNumber);
    if (Objects.nonNull(userByPhoneNumber)) {
      throw new ExceptionObjectAlreadyExists("Phone Number Already In Use");
    }

    User user = usersMapper.createUserFromRegisterRequest(request);
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
    try {
      Authentication auth = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
          request.getPhoneNumber(),
          request.getPassword()
        )
      );
      UserPrincipal user = (UserPrincipal) auth.getPrincipal();
      User loggedUser = user.getUser();
      this.userService.connectUser(loggedUser);
      String jwt = jwtService.generateToken(user);
      return AuthLoginResponse
        .builder()
        .userPhoneNumber(loggedUser.getPhoneNumber())
        .token(jwt)
        .build();
    } catch (AuthenticationException e) {
      throw new ExceptionObjectNotFound("Authentication failed. Bad credentials.");
    }
  }



}
