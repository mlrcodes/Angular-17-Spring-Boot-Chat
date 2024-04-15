package com.miguel.chatserver.EXCEPTIONS;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class ExceptionObjectNotFound extends RuntimeException {
  public ExceptionObjectNotFound(String message) {
    super(message);
  }

  public ExceptionObjectNotFound(String message, Throwable cause) {
    super(message, cause);
  }
}
