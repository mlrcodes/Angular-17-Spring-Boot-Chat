package com.miguel.chatserver.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageSaveDTO {

  @NotNull
  private Integer chatId;

  @NotBlank(message = "Message text required")
  private String messageText;

}
