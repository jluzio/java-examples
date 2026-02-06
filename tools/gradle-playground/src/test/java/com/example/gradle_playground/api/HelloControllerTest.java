package com.example.gradle_playground.api;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

@Slf4j
class HelloControllerTest {

  HelloController controller = new HelloController(new ObjectMapper());

  @Test
  void hello() {
    var message = controller.hello("test");
    log.info("hello :: {}", message);
    assertThat(message)
        .isEqualTo("Hello test");
  }
}