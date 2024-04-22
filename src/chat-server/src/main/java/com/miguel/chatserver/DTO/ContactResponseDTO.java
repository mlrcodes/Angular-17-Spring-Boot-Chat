package com.miguel.chatserver.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactResponseDTO {

  @NotNull
  private Integer contactId;

  @NotEmpty
  @NotBlank
  @Size(min = 2, max = 50)
  @Pattern(regexp = "^[A-ZÁÉÍÓÚÜÑ][a-záéíóúüñ]+(?: [A-ZÁÉÍÓÚÜÑ][a-záéíóúüñ]+)*$")
  private String contactName;

  @NotNull
  private UserDTO contactUser;
}
