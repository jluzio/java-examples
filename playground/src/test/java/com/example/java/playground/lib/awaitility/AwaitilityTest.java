package com.example.java.playground.lib.awaitility;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.awaitility.core.ConditionTimeoutException;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

class AwaitilityTest {

  @Test
  void simple() {
    var output = new ArrayList<>();

    Mono.just("some-element")
        .delayElement(Duration.ofMillis(200))
        .doOnNext(output::add)
        .block();

    await()
        .pollInterval(100, TimeUnit.MILLISECONDS)
        .atMost(300, TimeUnit.MILLISECONDS)
        .until(() -> !output.isEmpty());

    assertThat(output)
        .isEqualTo(List.of("some-element"));
  }

  @Test
  void same_thread() {
    var threadLocalValue = ThreadLocal.withInitial(() -> "initial");
    threadLocalValue.set("test");

    assertThatThrownBy(() ->
        await()
            .pollInterval(100, TimeUnit.MILLISECONDS)
            .atMost(300, TimeUnit.MILLISECONDS)
            .until(() -> Objects.equals(threadLocalValue.get(), "test"))
    ).isInstanceOf(ConditionTimeoutException.class);

    await()
        .pollInterval(100, TimeUnit.MILLISECONDS)
        .atMost(300, TimeUnit.MILLISECONDS)
        .pollInSameThread()
        .until(() -> Objects.equals(threadLocalValue.get(), "test"));
  }

}
