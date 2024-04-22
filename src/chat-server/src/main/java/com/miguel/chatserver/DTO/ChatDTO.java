package com.miguel.chatserver.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ChatDTO {

  private Integer chatId;

  private ContactResponseDTO contact;

}
