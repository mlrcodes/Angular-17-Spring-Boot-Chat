package com.miguel.chatserver.CONTROLLERS;

import com.miguel.chatserver.DTO.UserDTO;
import com.miguel.chatserver.SERVICES.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UsersController {

  @Autowired
  private IUserService userService;


  public ResponseEntity<UserDTO> getUserByPhoneNumber(@RequestParam String phoneNumber) {
    return ResponseEntity.ok(
      userService.getUserByPhoneNumber(phoneNumber)
    );
  }

}
