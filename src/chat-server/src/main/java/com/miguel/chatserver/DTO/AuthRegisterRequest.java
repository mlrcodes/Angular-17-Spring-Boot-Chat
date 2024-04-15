package com.miguel.chatserver.DTO;

import com.miguel.chatserver.VALIDATIONS.PasswordMatches;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatches
public class AuthRegisterRequest {

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

  @NotEmpty(message = "Email is required")
  @NotBlank(message = "Email is required")
  @Email(message = "Invalid email format")
  private String email;

  @NotEmpty(message = "Password is required")
  @NotBlank(message = "Password is required")
  @Size(min = 8, message = "Password must be at least 8 characters long")
  @Size(max = 16, message = "Password cannot exceed 16 character")
  @Pattern(
    regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%._\\-^&+=]).{8,16}$",
    message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character."
  )
  private String password;

  @NotEmpty(message = "Confirm password is required")
  @NotBlank(message = "Confirm password is required")
  private String confirmPassword;

  @NotNull(message= "Terms have to be accepted")
  @AssertTrue(message = "Terms have to be accepted")
  private Boolean acceptedTerms;
}
