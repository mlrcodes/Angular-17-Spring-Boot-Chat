package com.miguel.chatserver.EXCEPTIONS;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Controller
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
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
