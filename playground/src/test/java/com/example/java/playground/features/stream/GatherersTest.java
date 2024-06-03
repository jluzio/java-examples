package com.example.java.playground.features.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Gatherers;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class GatherersTest {

  @Test
  void fold() {
    var result = Stream.of(1, 2, 3, 4)
        .gather(Gatherers.fold(() -> "", (str, element) -> str + element))
        .findFirst()
        .orElseThrow();
    assertThat(result)
        .isEqualTo("1234");

    var result2 = Stream.of(1, 2, 3, 4)
        .gather(Gatherers.fold(() -> 0, (sum, element) -> sum + element))
        .findFirst()
        .orElseThrow();
    assertThat(result2)
        .isEqualTo(10);
  }

  @Test
  void scan() {
    var result = Stream.of(1, 2, 3, 4)
        .gather(Gatherers.scan(() -> "", (str, element) -> str + element))
        .findFirst()
        .orElseThrow();
    assertThat(result)
        .isEqualTo("1", "12", "123", "1234");

    var result2 = Stream.of(1, 2, 3, 4)
        .gather(Gatherers.scan(() -> 0, (sum, element) -> sum + element))
        .toList();
    assertThat(result2)
        .containsExactly(1, 3, 6, 10);
  }

  @Test
  void windowFixed() {
    var result = IntStream.rangeClosed(1, 9).boxed()
        .gather(Gatherers.windowFixed(4))
        .toList();
    assertThat(result)
        .containsExactly(List.of(1, 2, 3, 4), List.of(5, 6, 7, 8), List.of(9));
  }

  @Test
  void windowSliding() {
    var result = IntStream.rangeClosed(1, 5).boxed()
        .gather(Gatherers.windowSliding(3))
        .toList();
    assertThat(result)
        .containsExactly(List.of(1, 2, 3), List.of(2, 3, 4), List.of(3, 4, 5));
  }

  @Test
  void mapConcurrent() {
    AtomicReference<List<Integer>> interrupted = new AtomicReference<>(new ArrayList<>());
    var result = IntStream.rangeClosed(1, 5).boxed()
        .gather(Gatherers.mapConcurrent(2, n -> {
          try {
            Thread.sleep(n * 10);
          } catch (InterruptedException e) {
            System.out.println("Task %d was cancelled".formatted(n));
            Thread.currentThread().interrupt();
            interrupted.get().add(n);
          }
          return n;
        }))
        .limit(2)
        .toList();
    assertThat(result)
        .containsExactly(1, 2);
    // 5 doesn't start
    assertThat(interrupted.get())
        .containsExactlyInAnyOrder(3, 4);
  }

}
