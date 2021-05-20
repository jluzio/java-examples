package com.example.java.playground.lib.vavr;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class TryTest {

  record Response(String id, String text) {

  }

  @Test
  void try_on_success() {
    Try<Response> response = Try.of(() -> new Response("1", "text-1"));

    assertThat(response.isSuccess()).isTrue();
    response
        .onSuccess(r -> log.info("success"))
        .onFailure(r -> log.info("failure"))
        .andThen(r -> log.info("then"));

    String responseData = response
        .map(Response::text)
        .get();
    assertThat(responseData).isEqualTo("text-1");
  }

  @Test
  void try_on_exception() {
    Try<Response> response = Try.of(() -> {
      throw new IOException("unable to get");
    });

    assertThat(response.isFailure()).isTrue();
    response
        .onSuccess(r -> log.info("success"))
        .onFailure(r -> log.info("failure"))
        .andThen(r -> log.info("then"));

    String responseData = response
        .map(Response::text)
        .getOrElse("---error---");
    assertThat(responseData).isEqualTo("---error---");

    assertThatThrownBy(() -> response.get())
        .isInstanceOf(IOException.class);
  }

  private void throwingConsumer(String json) throws IOException {
    Integer value = new ObjectMapper().readValue(json, Integer.class);
    log.info("value: {}", value);
  }

  private Integer throwingFunction(String json) throws IOException {
    Integer value = new ObjectMapper().readValue(json, Integer.class);
    log.info("value: {}", value);
    return value;
  }

}
