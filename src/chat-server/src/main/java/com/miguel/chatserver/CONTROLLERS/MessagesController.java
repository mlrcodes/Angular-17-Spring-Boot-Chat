package com.miguel.chatserver.CONTROLLERS;

import com.miguel.chatserver.MODELS.Message;
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

  @GetMapping
  public ResponseEntity<List<Message>> getChatMessages (
    @RequestParam Integer chatId
  ) {
    return ResponseEntity.ok(
      this.messageService.getChatMessages(chatId)
    );
  }
}
