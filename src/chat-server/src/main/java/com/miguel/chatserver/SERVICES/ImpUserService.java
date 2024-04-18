package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.AuthRegisterRequest;
import com.miguel.chatserver.MODELS.User;
import com.miguel.chatserver.REPOSITORIES.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImpUserService implements IUserService {
  @Autowired
  private IUsersRepository userRepository;

  @Override
  public User registerUser(User user) {
    return this.userRepository.save(user);
  }

  @Override
  public User findByPhoneNumber(String phoneNumber) {
    return this.userRepository.findByPhoneNumber(phoneNumber).orElse(null);
  }

  @Override
  public User findByEmail(String email) {
    return this.userRepository.findByEmail(email).orElse(null);
  }

  @Override
  public User createUserFromRegisterRequest(AuthRegisterRequest request) {
    return User.builder()
        .firstname(request.getFirstname())
        .surname(request.getSurname())
        .phoneNumber(request.getPhoneNumber())
        .email(request.getEmail())
        .acceptedTerms(request.getAcceptedTerms())
      .build();
  }
}
