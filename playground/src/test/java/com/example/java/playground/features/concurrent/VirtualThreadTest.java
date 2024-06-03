package com.example.java.playground.features.concurrent;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

@Slf4j
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
  void test_thread() throws InterruptedException {
    Runnable runnable = () -> log.info("Inside Runnable");
    Thread thread = Thread.startVirtualThread(runnable);
//    Thread virtualThread = Thread.ofVirtual().start(runnable);
    thread.join();
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
