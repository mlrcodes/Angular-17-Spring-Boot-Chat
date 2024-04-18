package com.miguel.chatserver.MAPPERS;

import com.miguel.chatserver.DTO.AuthRegisterRequest;
import com.miguel.chatserver.DTO.UserDTO;
import com.miguel.chatserver.MODELS.User;

import java.util.List;

public interface IUsersMapper {
  public User createUserFromDTO(UserDTO userDTO);

  public List<User> createUserListFromDTOList(List<UserDTO> userDTOList);

  public UserDTO createUserDTOFromUser(User user);

  public List<UserDTO> createUserDTOListFromUserList(List<User> userList);

  public User createUserFromRegisterRequest(AuthRegisterRequest request);
}
