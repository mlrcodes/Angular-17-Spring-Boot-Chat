package com.miguel.chatserver.CONTROLLERS;

import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.SERVICES.IChatsService;
import com.miguel.chatserver.SERVICES.IContactService;
import com.miguel.chatserver.SERVICES.IMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessagesController {

  @Autowired
  private IMessageService messageService;

  @Autowired
  private IChatsService chatsService;

  @GetMapping
  public ResponseEntity<List<MessageDTO>> getChatMessages (
    @RequestParam Integer chatId
  ) {
    return ResponseEntity.ok(
      this.messageService.getChatMessagesDTO(
        chatsService.findById(chatId)
      )
    );
  }
}
