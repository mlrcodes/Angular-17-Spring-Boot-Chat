package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.AuthRegisterRequest;
import com.miguel.chatserver.DTO.UserDTO;
import com.miguel.chatserver.MODELS.User;

import java.util.List;

public interface IUserService {
  public User registerUser(User user);

  public User findByPhoneNumber(String phoneNumber);


  public User findByEmail(String phoneNumber);

  public User createUserFromRegisterRequest(AuthRegisterRequest request);

  public User createUserFromDTO(UserDTO userDTO);

  public List<User> createUserListFromDTOList(List<UserDTO> userDTOList);

  public UserDTO createUserDTOFromUser(User user);

  public List<UserDTO> createUserDTOListFromUserList(List<User> userList);
}
