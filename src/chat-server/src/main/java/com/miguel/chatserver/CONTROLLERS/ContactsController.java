package com.miguel.chatserver.CONTROLLERS;

import com.miguel.chatserver.DTO.ContactCreateRequest;
import com.miguel.chatserver.DTO.ContactEditRequest;
import com.miguel.chatserver.DTO.ContactResponseDTO;
import com.miguel.chatserver.DTO.ResultMessageDTO;
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
  private ResponseEntity<List<ContactResponseDTO>> getUserChats(
    HttpServletRequest request
  ) {
    return ResponseEntity.ok(
      contactService.getUserContacts(
        jwtService.getTokenFromRequestHeaders(request)
      )
    );
  }

  @PostMapping()
  private ResponseEntity<ContactResponseDTO> addContact(
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
  private ResponseEntity<ContactResponseDTO> updateContact(
    @RequestBody ContactEditRequest newContactName,
    @RequestParam Integer contactId
  ) {
    return ResponseEntity.ok(this.contactService.updateContact(contactId, newContactName));
  }

  @DeleteMapping
  private ResponseEntity<ResultMessageDTO> deleteContact(
    @RequestParam Integer contactId
  ) {
    return ResponseEntity.ok(this.contactService.deleteContact(contactId));
  }

}
