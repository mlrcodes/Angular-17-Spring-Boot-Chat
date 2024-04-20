package com.miguel.chatserver.CONTROLLERS;

import com.miguel.chatserver.DTO.ContactDTO;
import com.miguel.chatserver.DTO.ResultMessageDTO;
import com.miguel.chatserver.MODELS.Contact;
import com.miguel.chatserver.SERVICES.IContactService;
import com.miguel.chatserver.SERVICES.IJWTService;
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

  @PostMapping()
  private ResponseEntity<ContactDTO> addContact(
    @RequestBody ContactDTO contactDTO,
    HttpServletRequest request
  ) {
    return ResponseEntity.ok(
      contactService.createContact(
        contactDTO,
        jwtService.getTokenFromRequestHeaders(request)
      )
    );
  }

  @DeleteMapping
  private ResponseEntity<ResultMessageDTO> deleteContact(
    @RequestParam Integer contactId
  ) {
    return ResponseEntity.ok(this.contactService.deleteContact(contactId));
  }

}
