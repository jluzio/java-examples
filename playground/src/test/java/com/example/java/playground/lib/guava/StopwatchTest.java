package com.example.java.playground.lib.guava;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.base.Stopwatch;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

@Slf4j
class StopwatchTest {

  @Test
  void test() {
    var stopwatch = Stopwatch.createStarted();
    Mono.delay(Duration.ofMillis(100)).block();
    var elapsed = stopwatch.stop().elapsed();
    assertThat(elapsed).isGreaterThanOrEqualTo(Duration.ofMillis(100));
    log.info("elapsed: {}", stopwatch);
  }

}
