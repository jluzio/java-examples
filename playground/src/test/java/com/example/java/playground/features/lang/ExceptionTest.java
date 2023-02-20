package com.example.java.playground.features.lang;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class ExceptionTest {

  @Test
  void initCause() {
    var cause = new IllegalArgumentException("Some runtime exception");
    var throwable = new IndexOutOfBoundsException("Index was 0").initCause(cause);
    log.info("throwable", throwable);
    Assertions.assertThatThrownBy(
            () -> {
              throw throwable;
            })
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasCauseInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void initCause_invalid() {
    var cause = new IllegalArgumentException("Some runtime exception");
    var anotherCause = new UnsupportedOperationException("Another runtime exception");
    var throwable = new IOException("Index was 0", cause);
    Assertions.assertThatThrownBy(
            () -> {
              throw throwable.initCause(anotherCause);
            })
        .isInstanceOf(IllegalStateException.class);
  }
}
