package com.miguel.chatserver.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthLoginRequest {

  @NotEmpty(message = "Phone Number is required")
  @NotBlank(message = "Phone Number is required")
  @Pattern(regexp = "^\\+?\\d{1,3}\\d{1,14}$", message = "Must provide a valid phone number")
  private String phoneNumber;

  @NotEmpty(message = "Password is required")
  @NotBlank(message = "Password is required")
  @Pattern(
    regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%._\\-^&+=]).{8,16}$",
    message = "Invalid password format"
  )
  private String password;

}
