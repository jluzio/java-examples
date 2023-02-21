package com.example.java.playground.features.concurrency;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

class VirtualThreadTest {

  @Test
  void virtual_threads() {
    // finish within 1 second
    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      IntStream.range(0, 10_000).forEach(i -> {
        executor.submit(() -> {
          Thread.sleep(Duration.ofSeconds(1));
          return i;
        });
      });
    }
  }

  @Test
  @EnabledIfEnvironmentVariable(named = "long_test_enabled", matches = "true")
  void threads() {
    // 100/20 = 5 seconds-ish
    try (var executor = Executors.newFixedThreadPool(20)) {
      IntStream.range(0, 100).forEach(i -> {
        executor.submit(() -> {
          Thread.sleep(Duration.ofSeconds(1));
          return i;
        });
      });
    }
  }

}
