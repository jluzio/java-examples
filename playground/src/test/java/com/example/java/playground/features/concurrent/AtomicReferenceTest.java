package com.example.java.playground.features.concurrent;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class AtomicReferenceTest {

  @Test
  void test() {
    AtomicReference<Integer> ref = new AtomicReference<>(0);
    Stream.of(1, 2, 5, 6, 2).forEach(v -> {
      if (ref.get() < v) {
        ref.set(v);
      }
    });
    log.info("max: {}", ref.get());
  }

}
