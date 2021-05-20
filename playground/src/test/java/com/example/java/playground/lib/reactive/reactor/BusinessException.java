package com.example.java.playground.lib.reactive.reactor;

public class BusinessException extends RuntimeException {

  public BusinessException() {
  }

  public BusinessException(String message) {
    super(message);
  }

  public BusinessException(String message, Throwable cause) {
    super(message, cause);
  }
}
