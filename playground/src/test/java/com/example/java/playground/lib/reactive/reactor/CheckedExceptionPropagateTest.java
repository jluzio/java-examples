package com.example.java.playground.lib.reactive.reactor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.Exceptions;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

@Slf4j
class CheckedExceptionPropagateTest {

  @Test
  void test_propagate() {
    var checkedException = checkedException();
    var valuePublisher = baseValuePublisher()
        .map(v -> {
          if (Objects.equals(v, "42")) {
            throw Exceptions.propagate(new IOException("Can't handle all the info"));
          }
          return v;
        });
    assertExceptionHandling(valuePublisher, checkedException);
  }

  @Test
  void test_handle() {
    var checkedException = checkedException();
    var valuePublisher = baseValuePublisher()
        .handle((String v, SynchronousSink<String> sink) -> {
          if (Objects.equals(v, "42")) {
            sink.error(new IOException("Can't handle all the info"));
          } else {
            sink.next(v);
          }
        });
    assertExceptionHandling(valuePublisher, checkedException);
  }

  @Test
  void test_flatMap() {
    var checkedException = checkedException();
    var valuePublisher = baseValuePublisher()
        .flatMap(v -> Objects.equals(v, "42")
            ? Mono.error(checkedException)
            : Mono.just(v));
    assertExceptionHandling(valuePublisher, checkedException);
  }

  @SuppressWarnings("java:S5778")
  private void assertExceptionHandling(Mono<String> publisher, Throwable throwable) {
    assertThatThrownBy(() -> publisher
        .doOnError(t -> log.info("Error :: {} | {}", t.getMessage(), t.getClass().getName()))
        .doOnError(t -> {
          log.info("assert of {} :: {}", throwable.getClass().getName(), t.getClass().getName());
          assertThat(t)
              .isInstanceOf(throwable.getClass());
        })
        .block())
        .isInstanceOf(RuntimeException.class)
        .hasCauseInstanceOf(throwable.getClass());
  }

  private Mono<String> baseValuePublisher() {
    return Mono.just("42");
  }

  private Throwable checkedException() {
    return new IOException("Can't handle all the info");
  }

}
