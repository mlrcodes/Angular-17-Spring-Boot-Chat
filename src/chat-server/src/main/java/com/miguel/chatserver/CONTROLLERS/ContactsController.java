package com.miguel.chatserver.CONTROLLERS;

import com.miguel.chatserver.DTO.ContactDTO;
import com.miguel.chatserver.SERVICES.IContactService;
import com.miguel.chatserver.SERVICES.IJWTService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactsController {

  @Autowired
  private IContactService contactService;

  @Autowired
  private IJWTService jwtService;

  @GetMapping()
  private ResponseEntity<List<ContactDTO>> getUserChats(
    HttpServletRequest request
  ) {
    return ResponseEntity.ok(
      contactService.getUserContacts(
        jwtService.getTokenFromRequestHeaders(request)
      )
    );
  }

}
