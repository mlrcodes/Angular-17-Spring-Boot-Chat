package com.miguel.chatserver.MAPPERS;

import com.miguel.chatserver.DTO.UserDTO;
import com.miguel.chatserver.MODELS.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ImpUsersMapper implements IUsersMapper {

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
