package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.AuthRegisterRequest;
import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.MODELS.User;
import com.miguel.chatserver.REPOSITORIES.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImpUserService implements IUserService {
  @Autowired
  private IUserRepository userRepository;
  @Autowired
  private IRoleService roleService;

  @Override
  public User registerUser(User user) {
    return this.userRepository.save(user);
  }

  @Override
  public User findByPhoneNumber(String phoneNumber) {
    return this.userRepository.findByPhoneNumber(phoneNumber).orElse(null);
  }

  @Override
  public Boolean existsPhoneNumber(String phoneNumber) {
    return this.userRepository.existsPhoneNumber(phoneNumber);
  }

  @Override
  public User createUserFromRegisterRequest(AuthRegisterRequest request) {
    return new User(
      request.getFirstname(),
      request.getSurname(),
      request.getPhoneNumber(),
      request.getEmail(),
      request.getPassword(),
      List.of(this.roleService.findByName("USER"))
    );
  }
}
