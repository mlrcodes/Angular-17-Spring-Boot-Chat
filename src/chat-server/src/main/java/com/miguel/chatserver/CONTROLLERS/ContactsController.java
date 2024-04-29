package com.miguel.chatserver.CONTROLLERS;

import com.miguel.chatserver.DTO.*;
import com.miguel.chatserver.SERVICES.IChatsService;
import com.miguel.chatserver.SERVICES.IContactService;
import com.miguel.chatserver.SERVICES.IJWTService;
import com.miguel.chatserver.SERVICES.IUsersService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactsController {

  @Autowired
  private IContactService contactService;

  @Autowired
  private IJWTService jwtService;

  @Autowired
  private IUsersService usersService;

  @PostMapping()
  private ResponseEntity<ChatDTO> addContact(
    @RequestBody ContactCreateRequest contactRequest,
    HttpServletRequest request
  ) {
    return ResponseEntity.ok(
      contactService.createContact(
        contactRequest,
        jwtService.getTokenFromRequestHeaders(request)
      )
    );
  }

  @PutMapping
  private ResponseEntity<ChatDTO> updateContact(
    @RequestBody ContactEditRequest newContactName,
    @RequestParam Integer contactId,
    HttpServletRequest request
  ) {
    return ResponseEntity.ok(
      contactService.getChatAfterContactUpdate(
        usersService.findByPhoneNumber(
          jwtService.getPhoneNumberFromToken(
            jwtService.getTokenFromRequestHeaders(request)
          )
        ),
        contactId,
        newContactName
      )
    );
  }

  @DeleteMapping
  private ResponseEntity<ResultMessageDTO> deleteContact(
    @RequestParam Integer contactId
  ) {
    return ResponseEntity.ok(
      this.contactService.deleteContact(contactId)
    );
  }

}
