package com.miguel.chatserver.EXCEPTIONS;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
  private String message;

  private Integer status;

  private String statusText;

  private List<ValidationError> validationErrors;

  public ErrorResponse(String message, HttpStatus status) {
    this.message = message;
    this.status = status.value();
    this.statusText = status.getReasonPhrase();
  }

  @Getter @Setter
  @AllArgsConstructor
  private static class ValidationError {
    private final String field;
    private final String message;
  }

  public void addValidationError(String field, String message){
    if (Objects.isNull(this.validationErrors)){
      validationErrors = new ArrayList<>();
    }
    validationErrors.add(new ValidationError(field, message));
  }
}
