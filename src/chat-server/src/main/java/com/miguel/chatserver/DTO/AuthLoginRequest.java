package com.miguel.chatserver.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthLoginRequest {

  private String phoneNumber;

  private String password;

}
