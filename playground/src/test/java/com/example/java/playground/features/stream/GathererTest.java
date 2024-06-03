package com.example.java.playground.features.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Function;
import java.util.stream.Gatherer;
import java.util.stream.Gatherer.Integrator;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class GathererTest {

  @Test
  void map_gatherer() {
    var data = Stream.of(1, 2, 3, 4)
        .gather(map(v -> v + 1))
        .toList();
    assertThat(data)
        .containsExactly(2, 3, 4, 5);
  }

  <T, R> Gatherer<T, ?, R> map(Function<? super T, ? extends R> mapper) {
    Integrator<Void, T, R> integrator = (_, element, downstream) ->
        downstream.push(mapper.apply(element));
    return Gatherer.of(integrator);
  }

}
