package com.miguel.chatserver.CONTROLLERS;

import com.miguel.chatserver.DTO.AuthRegisterRequest;
import com.miguel.chatserver.DTO.AuthRegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessagesController {

  @GetMapping("/getMessage")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<String> getMessage () {
    return ResponseEntity.ok("BIEN");
  }
}
