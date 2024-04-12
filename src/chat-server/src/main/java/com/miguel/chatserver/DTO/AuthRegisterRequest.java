package com.miguel.chatserver.DTO;

import com.miguel.chatserver.VALIDATIONS.ConfirmPasswordMatchesPassword;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ConfirmPasswordMatchesPassword.List({
  @ConfirmPasswordMatchesPassword(
    password = "password",
    confirmPassword = "confirmPassword",
    message = "Confirm password do not match"
  )
})public class AuthRegisterRequest {

  @NotEmpty(message = "Firstname is required")
  @NotBlank(message = "Firstname is required")
  @Pattern(regexp = "^[a-zA-ZÁÉÍÓÚáéíóúÑñ]+(\\s[A-Za-zÁÉÍÓÚáéíóúÑñ]+)*$", message = "Must introduce a valid first name")
  private String firstname;

  @NotEmpty(message = "Surname is required")
  @NotBlank(message = "Surname is required")
  @Pattern(regexp = "^[a-zA-ZÁÉÍÓÚáéíóúÑñ]+(\\s[A-Za-zÁÉÍÓÚáéíóúÑñ]+)*$", message = "Must introduce a valid last name")
  private String surname;

  @NotEmpty(message = "Phone Number is required")
  @NotBlank(message = "Phone Number is required")
  @Pattern(regexp = "^\\+?\\d{1,3}\\d{1,14}$", message = "Must provide a valid phone number")
  private String phoneNumber;

  @NotEmpty(message = "Email is required")
  @NotBlank(message = "Email is required")
  @Pattern(
    regexp = "^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,}$\n",
    message = "Invalid email format"
  )
  private String email;

  @NotEmpty(message = "Password is required")
  @NotBlank(message = "Password is required")
  @Size(min = 8, message = "Password must be at least 8 characters long")
  @Size(max = 16, message = "Confirm password cannot exceed 16 character")
  @Pattern(
    regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@_#-$.%^&+=!])(?=\\S+$).*$",
    message = "Password must contain at least one digit, one uppercase letter, one lowercase letter and one special character"
  )
  private String password;

  @NotEmpty(message = "Confirm password is required")
  @NotBlank(message = "Confirm password is required")
  @Size(min = 8, message = "Confirm password must be at least 8 characters long")
  @Size(max = 16, message = "Confirm password cannot exceed 16 character")
  @Pattern(
    regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@_#-$.%^&+=!])(?=\\S+$).*$",
    message = "Confirm password must contain at least one digit, one uppercase letter, one lowercase letter and one special character"
  )
  private String confirmPassword;

  @NotEmpty(message = "Terms have to be accepted")
  @AssertTrue(message = "Terms have to be accepted")
  private Boolean acceptedTerms;
}
