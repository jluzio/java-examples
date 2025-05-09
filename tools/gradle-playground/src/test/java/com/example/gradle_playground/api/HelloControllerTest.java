package com.example.gradle_playground.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class HelloControllerTest {

  HelloController controller = new HelloController(new ObjectMapper());

  @Test
  void hello() throws IOException {
    var message = controller.hello("test");
    log.info("hello :: {}", message);
    assertThat(message)
        .isEqualTo("Hello test");
  }
}