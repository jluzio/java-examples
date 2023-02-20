package com.example.java.playground.lib.log;

import static org.assertj.core.api.Assertions.assertThatCode;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

@Log4j2
class LambdaLogTest {

  @Test
  void lambda_log() {
    assertThatCode(() -> log.info("using lambda: {}", () -> "expensive value"))
        .doesNotThrowAnyException();
  }

}
