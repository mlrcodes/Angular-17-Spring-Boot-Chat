package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.AuthRegisterRequest;
import com.miguel.chatserver.DTO.UserDTO;
import com.miguel.chatserver.MODELS.User;
import com.miguel.chatserver.REPOSITORIES.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

  @Override
  public User createUserFromDTO(UserDTO userDTO) {
    return User
      .builder()
      .firstname(userDTO.getFirstname())
      .surname(userDTO.getSurname())
      .phoneNumber(userDTO.getPhoneNumber())
      .phoneNumber(userDTO.getEmail())
      .build();
  }

  @Override
  public List<User> createUserListFromDTOList(List<UserDTO> userDTOList) {
    List<User> userList = new ArrayList<>();
    for (UserDTO userDTO: userDTOList) {
      userList.add(
        this.createUserFromDTO(userDTO)
      );
    }
    return userList;
  }

  @Override
  public UserDTO createUserDTOFromUser(User user) {
    return UserDTO
      .builder()
      .firstname(user.getFirstname())
      .surname(user.getSurname())
      .phoneNumber(user.getPhoneNumber())
      .email(user.getEmail())
      .build();
  }

  @Override
  public List<UserDTO> createUserDTOListFromUserList(List<User> userList) {
    List userDTOList = new ArrayList<>();
    for (User user: userList) {
      userDTOList.add(
        this.createUserDTOFromUser(user)
      );
    }
    return userDTOList;
  }
}
