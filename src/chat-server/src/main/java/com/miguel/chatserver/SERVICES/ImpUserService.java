package com.miguel.chatserver.SERVICES;

import com.miguel.chatserver.DTO.UserDTO;
import com.miguel.chatserver.EXCEPTIONS.ExceptionObjectNotFound;
import com.miguel.chatserver.MAPPERS.IUsersMapper;
import com.miguel.chatserver.MODELS.Status;
import com.miguel.chatserver.MODELS.User;
import com.miguel.chatserver.REPOSITORIES.IUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ImpUserService implements IUsersService {
  @Autowired
  private IUsersRepository userRepository;

  @Autowired
  private IUsersMapper usersMapper;

  @Override
  public User registerUser(User user) {
    return this.userRepository.save(user);
  }

  @Override
  public UserDTO getUserByPhoneNumber(String phoneNumber) {
    User user = this.findByPhoneNumber(phoneNumber);
    if (Objects.isNull(user)) {
      throw new ExceptionObjectNotFound("No user was found");
    }
    return usersMapper.createUserDTOFromUser(user);
  }

  @Override
  public User findByPhoneNumber(String phoneNumber) {
    return this.userRepository.findByPhoneNumber(phoneNumber).orElse(null);
  }

  @Override
  public void connectUser(User user) {
    user.setStatus(Status.OFFLINE);
    this.userRepository.save(user);
  }

  public void disconnect(User user) {
    User savedUser = userRepository.findByPhoneNumber(user.getPhoneNumber()).orElse(null);
    if (Objects.nonNull(savedUser)) {
      savedUser.setStatus(Status.OFFLINE);
      userRepository.save(savedUser);
    }
  }
}
