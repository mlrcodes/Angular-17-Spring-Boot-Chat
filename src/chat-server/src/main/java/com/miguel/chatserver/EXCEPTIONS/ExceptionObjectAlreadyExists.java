package com.miguel.chatserver.EXCEPTIONS;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExceptionObjectAlreadyExists extends RuntimeException {

  public ExceptionObjectAlreadyExists(String message) {
    super(message);
  }

  public ExceptionObjectAlreadyExists(String message, Throwable cause) {
    super(message, cause);
  }

}
