package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.AuthLoginRequest;
import com.miguel.chatserver.DTO.AuthLoginResponse;
import com.miguel.chatserver.DTO.AuthRegisterRequest;
import com.miguel.chatserver.DTO.AuthRegisterResponse;
import com.miguel.chatserver.EXCEPTIONS.ExceptionObjectAlreadyExists;
import com.miguel.chatserver.EXCEPTIONS.ExceptionObjectNotFound;
import com.miguel.chatserver.MODELS.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ImpAuthenticationService implements IAuthenticationService{

  @Autowired
  private IUserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private IJWTService jwtService;

  @Override
  public AuthRegisterResponse register(AuthRegisterRequest request) {

    String phoneNumber = request.getPhoneNumber();
    Boolean existsPhoneNumber = this.userService.existsPhoneNumber(phoneNumber);
    if (existsPhoneNumber) {
      throw new ExceptionObjectAlreadyExists("Phone Number Already In Use");
    }

    User user = this.userService.createUserFromRegisterRequest(request);
    String hashedPassword = passwordEncoder.encode(request.getPassword());
    user.setPassword(hashedPassword);

    try {
      this.userService.registerUser(user);
      return new AuthRegisterResponse("User registered successfully");
    } catch (Exception ex) {
      throw ex;
    }
  }

  @Override
  public AuthLoginResponse login(AuthLoginRequest request) {

    String phoneNumber = request.getPhoneNumber();
    User user = this.userService.findByPhoneNumber(phoneNumber);
    if (Objects.isNull(user)) {
      throw new ExceptionObjectNotFound("User Does Not Exist");
    }
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getPhoneNumber(), request.getPassword()));
    UserDetails userDetails = new User();
    String token = jwtService.getToken(userDetails);
    return new AuthLoginResponse(token);
  }
}
