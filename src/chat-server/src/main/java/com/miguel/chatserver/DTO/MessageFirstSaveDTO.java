package com.miguel.chatserver.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageFirstSaveDTO {

  private String contactPhoneNumber;
  private String messageText;

}
