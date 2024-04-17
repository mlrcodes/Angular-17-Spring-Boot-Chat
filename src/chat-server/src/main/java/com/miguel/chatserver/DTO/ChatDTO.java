package com.miguel.chatserver.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatDTO {

  @NonNull
  private List<UserDTO> members;

  @NonNull
  private List<MessageDTO> messages;
}
