package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.AuthLoginRequest;
import com.miguel.chatserver.DTO.AuthLoginResponse;
import com.miguel.chatserver.DTO.AuthRegisterRequest;
import com.miguel.chatserver.DTO.AuthRegisterResponse;
import com.miguel.chatserver.EXCEPTIONS.ExceptionObjectAlreadyExists;
import com.miguel.chatserver.EXCEPTIONS.ExceptionObjectNotFound;
import com.miguel.chatserver.MODELS.EmailTemplateName;
import com.miguel.chatserver.MODELS.Token;
import com.miguel.chatserver.MODELS.User;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
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

  @Autowired
  private IEmailService emailService;


  @Override
  public AuthRegisterResponse register(AuthRegisterRequest request) {
    String phoneNumber = request.getPhoneNumber();
    Boolean existsPhoneNumber = this.userService.existsPhoneNumber(phoneNumber);
    if (existsPhoneNumber) {
      throw new ExceptionObjectAlreadyExists("Phone Number Already In Use");
    }
    request.setPassword(passwordEncoder.encode(request.getPassword()));
    User user = this.userService.createUserFromRegisterRequest(request);
    try {
      this.userService.registerUser(user);
      sendValidationEmail(user);
      return new AuthRegisterResponse("User registered successfully");
    } catch (MessagingException messagingException) {
      return null;
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
    String token = jwtService.generateToken(userDetails);
    return new AuthLoginResponse(token);
  }

  private void sendValidationEmail(User user) throws MessagingException {

    String activationToken = generateAndSaveActivationToken(user);
    emailService.sendEmail(
      user.getEmail(),
      user.getFirstname() + " " + user.getSurname(),
      EmailTemplateName.ACTIVATE_ACCOUNT,
      activationUrl,
      activationToken,
      "Account Activation"
    );
  }
  private String generateAndSaveActivationToken(User user) {
    String activationToken = generateActivationCode(6);
    Token token = new Token(
      activationToken,
      LocalDateTime.now(),
      LocalDateTime.now().plusMinutes(15),
      user
    );
    jwtService.saveToken(token);
    return activationToken;
  }

  private String generateActivationCode(Integer length) {
    String characters = "0123456789";
    StringBuilder codeBuilder = new StringBuilder();
    SecureRandom secureRandom = new SecureRandom();
    for (int i = 0; i < length; i++) {
      int randomIndex = secureRandom.nextInt(characters.length());
      codeBuilder.append(characters.charAt(randomIndex));
    }
    return codeBuilder.toString();
  }
}
