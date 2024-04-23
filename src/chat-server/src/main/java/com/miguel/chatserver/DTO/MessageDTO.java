package com.miguel.chatserver.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

  private Integer messageId;

  @NotNull(message = "Message's sender required")
  private String senderPhoneNumber;

  private Date timestamp;

  @NotBlank(message = "Message text required")
  private String messageText;

}
