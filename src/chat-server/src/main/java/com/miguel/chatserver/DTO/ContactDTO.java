package com.miguel.chatserver.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDTO {

  @NotEmpty(message = "Contact name is required")
  @NotBlank(message = "Contact name is required")
  @Size(min = 2, max = 50, message = "Invalid contact name size")
  @Pattern(regexp = "^[A-ZÁÉÍÓÚÜÑ][a-záéíóúüñ]+(?: [A-ZÁÉÍÓÚÜÑ][a-záéíóúüñ]+)*$", message = "Must introduce a valid contact name")
  private String contactName;

  private UserDTO owner;

  @NotNull
  private UserDTO contactUser;

}
