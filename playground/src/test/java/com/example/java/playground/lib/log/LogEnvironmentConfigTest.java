package com.example.java.playground.lib.log;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

@SpringBootTest
@ExtendWith(SystemStubsExtension.class)
@Slf4j
class LogEnvironmentConfigTest {

  @Configuration
  static class Config {

  }

  // In this test: both work and SCREAMING_SNAKE_CASE wins
  // NOTE: in some apps (at least containers) it seems that the variables may get ignored
  // NOTE: need to investigate why, maybe it's Kubernetes related
  // NOTE: VM variables can be used instead (-Dvar=value)
  @SystemStub
  static EnvironmentVariables envVars = new EnvironmentVariables()
      .set("LOGGING_LEVEL_COM_EXAMPLE_JAVA_PLAYGROUND", "WARN")
      .set("logging.level.com.example.java.playground", "INFO");

  @Test
  void test() {
    log.debug("debug");
    log.info("info");
    log.warn("warn");
  }

}
