package com.miguel.chatserver.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.w3c.dom.Text;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactCreateRequest {

  @NotEmpty(message = "Contact phone number is required")
  @NotBlank(message = "Contact phone number is required")
  @Pattern(regexp = "^\\+?\\d{1,3}\\d{1,14}$", message = "Must provide a valid contact phone number")
  private String contactPhoneNumber;

  @NotEmpty(message = "Contact name is required")
  @NotBlank(message = "Contact name is required")
  @Size(min = 2, max = 50, message = "Invalid contact name size")
  @Pattern(regexp = "^[A-ZÁÉÍÓÚÜÑ][a-záéíóúüñ]+(?: [A-ZÁÉÍÓÚÜÑ][a-záéíóúüñ]+)*$", message = "Must introduce a valid contact name")
  private String contactName;

  @Size(max = 1000, message = "Message cannot exceed 300 characters")
  private String message;

}
