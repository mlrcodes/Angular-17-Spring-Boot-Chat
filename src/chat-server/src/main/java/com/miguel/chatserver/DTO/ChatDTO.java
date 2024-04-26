package com.miguel.chatserver.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ChatDTO {

  private Integer chatId;

  private ContactResponseDTO contact;

}
