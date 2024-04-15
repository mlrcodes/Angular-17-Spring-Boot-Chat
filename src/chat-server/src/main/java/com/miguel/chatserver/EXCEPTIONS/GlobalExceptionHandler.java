package com.miguel.chatserver.EXCEPTIONS;

import com.miguel.chatserver.DTO.AuthRegisterResponse;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    MethodArgumentNotValidException ex,
    HttpHeaders headers,
    HttpStatusCode status,
    WebRequest request
  ) {
    HttpStatus httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;

    ErrorResponse errorResponse = new ErrorResponse(
      "Validation error. Check 'errors' field for details.",
      HttpStatus.UNPROCESSABLE_ENTITY
    );

    for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
      errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
    }

    return new ResponseEntity<>(errorResponse, httpStatus);
  }

  /*
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exp) {

    Set<String> errors = new HashSet<>();
    exp.getBindingResult().getAllErrors()
      .forEach(error -> {
          var errorMessage = error.getDefaultMessage();
          errors.add(errorMessage);
        }
      );
    return ResponseEntity
      .status(BAD_REQUEST)
      .body(
        ExceptionResponse.builder()
          .validationErrors(errors)
          .build()
      );
  }
  */

  @ExceptionHandler(ExceptionObjectAlreadyExists.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<Object> handleExceptionObjectAlreadyExists(ExceptionObjectAlreadyExists ex) {

    HttpStatus httpStatus = HttpStatus.CONFLICT;

    ErrorResponse errorResponse = new ErrorResponse(
      ex.getMessage(),
      httpStatus
    );

    return new ResponseEntity<>(errorResponse, httpStatus);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(value = ExceptionObjectNotFound.class)
  public ResponseEntity<Object> handleExceptionObjectNotFound(ExceptionObjectNotFound ex) {

    HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    ErrorResponse errorResponse = new ErrorResponse(
      ex.getMessage(),
      httpStatus
    );

    return new ResponseEntity<>(errorResponse, httpStatus);
  }
}
