package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.AuthRegisterRequest;
import com.miguel.chatserver.MODELS.User;
import com.miguel.chatserver.REPOSITORIES.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImpUserService implements IUserService {
  @Autowired
  private IUserRepository userRepository;

  @Override
  public User registerUser(User user) {
    return this.userRepository.save(user);
  }

  @Override
  public User findByPhoneNumber(String phoneNumber) {
    return this.userRepository.findByPhoneNumber(phoneNumber).orElse(null);
  }

  @Override
  public User createUserFromRegisterRequest(AuthRegisterRequest request) {
    return User.builder()
      .password(request.getPassword())
      .build();
  }
}
