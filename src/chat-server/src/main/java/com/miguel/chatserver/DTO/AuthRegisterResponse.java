package com.miguel.chatserver.DTO;

import lombok.*;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRegisterResponse {

  private String phoneNumber;
  private Boolean success;
  private String message;

}
