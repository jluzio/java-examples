package com.example.java.playground.utils;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Functions {

  public <T> UnaryOperator<T> loggingIdentity(String msg) {
    return value -> {
      log.info(msg, value);
      return value;
    };
  }

  public <T> Consumer<T> logging(String msg) {
    return value -> log.info(msg, value);
  }

}
