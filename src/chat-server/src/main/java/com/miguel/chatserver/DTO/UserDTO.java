package com.miguel.chatserver.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

  @NotEmpty(message = "Firstname is required")
  @NotBlank(message = "Firstname is required")
  @Size(min = 2, max = 50, message = "Invalid firstname size")
  @Pattern(regexp = "^[A-ZÁÉÍÓÚÜÑ][a-záéíóúüñ]+(?: [A-ZÁÉÍÓÚÜÑ][a-záéíóúüñ]+)*$", message = "Must introduce a valid firstname")
  private String firstname;

  @NotEmpty(message = "Surname is required")
  @NotBlank(message = "Surname is required")
  @Size(min = 2, max = 50, message = "Invalid surname size")
  @Pattern(regexp = "^[A-ZÁÉÍÓÚÜÑ][a-záéíóúüñ]+(?: [A-ZÁÉÍÓÚÜÑ][a-záéíóúüñ]+)*$", message = "Must introduce a valid surname")
  private String surname;

  @NotEmpty(message = "Phone Number is required")
  @NotBlank(message = "Phone Number is required")
  @Pattern(regexp = "^\\+?\\d{1,3}\\d{1,14}$", message = "Must provide a valid phone number")
  private String phoneNumber;
}
