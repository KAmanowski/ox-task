package com.kacper.oxtask.exception;

public class NotFoundRepositoryException extends RuntimeException {

  public NotFoundRepositoryException(String message, Throwable cause) {
    super(message, cause);
  }
}
