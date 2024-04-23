package com.miguel.chatserver.CONTROLLERS;

import com.miguel.chatserver.DTO.MessageDTO;
import com.miguel.chatserver.DTO.MessageSaveDTO;
import com.miguel.chatserver.SERVICES.IMessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessagesController {

  @Autowired
  private IMessageService messageService;


  @Autowired
  private SimpMessagingTemplate messagingTemplate;


  @GetMapping
  public ResponseEntity<List<MessageDTO>> getChatMessages (
    @RequestParam Integer chatId
  ) {
    return ResponseEntity.ok(
      this.messageService.getChatMessages(chatId)
    );
  }

  @MessageMapping("/chat")
  public void processMessage(
    @Payload MessageSaveDTO saveMessageDTO
  ) {
    MessageDTO savedMessageDTO = messageService.saveMessage(saveMessageDTO);
    messagingTemplate.convertAndSendToUser(
      savedMessageDTO.getMessageText(),
      "/queue/messages",
      savedMessageDTO
    );
  }


}
