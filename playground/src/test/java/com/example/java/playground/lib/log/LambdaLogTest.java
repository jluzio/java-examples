package com.example.java.playground.lib.log;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

@Log4j2
class LambdaLogTest {

  @Test
  void lambda_log() {
    log.debug("using lambda: {}", () -> "expensive value");
  }

}
