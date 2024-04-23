package com.miguel.chatserver.CONTROLLERS;

import com.miguel.chatserver.DTO.ChatDTO;
import com.miguel.chatserver.SERVICES.IChatsService;
import com.miguel.chatserver.SERVICES.IContactService;
import com.miguel.chatserver.SERVICES.IJWTService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatsController {

  @Autowired
  private IChatsService chatsService;

  @Autowired
  private IContactService contactsService;

  @Autowired
  private IJWTService jwtService;

  @GetMapping
  private ResponseEntity<List<ChatDTO>> getUserChats(
    HttpServletRequest request
  ) {
    return ResponseEntity.ok(
      chatsService.getUserChats(
        jwtService.getTokenFromRequestHeaders(request)
      )
    );
  }

  @PostMapping
  private ResponseEntity<ChatDTO> createChat(
    @RequestParam String contactPhoneNumber
  ) {
    return ResponseEntity.ok(
      chatsService.createChat(
        contactsService.getContactFromContactPhoneNumber(
          contactPhoneNumber
        )
      )
    );
  }

}
