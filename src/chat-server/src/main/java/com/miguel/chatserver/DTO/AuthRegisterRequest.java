package com.miguel.chatserver.DTO;

import com.miguel.chatserver.VALIDATIONS.ConfirmPasswordMatchesPassword;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

  @NotBlank(message = "Firstname is required")
  @Pattern(regexp = "^[a-zA-ZÁÉÍÓÚáéíóúÑñ]+(\\s[A-Za-zÁÉÍÓÚáéíóúÑñ]+)*$", message = "Must introduce a valid first name")
  private String firstname;

  @NotBlank(message = "Surname is required")
  @Pattern(regexp = "^[a-zA-ZÁÉÍÓÚáéíóúÑñ]+(\\s[A-Za-zÁÉÍÓÚáéíóúÑñ]+)*$", message = "Must introduce a valid last name")
  private String surname;

  @NotBlank(message = "Phone Number is required")
  @Pattern(regexp = "^\\+?\\d{1,3}\\d{1,14}$", message = "Must provide a valid phone number")
  private String phoneNumber;
  @NotBlank(message = "Password is required")
  @Pattern(
    regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@_#-$.%^&+=!])(?=\\S+$).*$",
    message = "Password must contain at least one digit, one uppercase letter, one lowercase letter and one special character"
  )
  private String password;

  @NotBlank(message = "Confirm password is required")
  @Pattern(
    regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@_#-$.%^&+=!])(?=\\S+$).*$",
    message = "Confirm password must contain at least one digit, one uppercase letter, one lowercase letter and one special character"
  )
  private String confirmPassword;

  @AssertTrue(message = "Terms have to be accepted")
  private Boolean acceptedTerms;
}
