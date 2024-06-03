package com.example.java.playground.features.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Gatherer;
import java.util.stream.Gatherer.Downstream;
import java.util.stream.Gatherer.Integrator;
import java.util.stream.IntStream;
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

  @Test
  void mapMulti_gatherer() {
    // example similar to flatMap
    var data = Stream.of(List.of(1, 2), List.of(3, 4))
        .gather(mapMulti(List::forEach))
        .toList();
    assertThat(data)
        .containsExactly(1, 2, 3, 4);
  }

  <T, R> Gatherer<T, ?, R> mapMulti(BiConsumer<? super T, Consumer<? super R>> mapper) {
    Integrator<Void, T, R> integrator = (_, element, downstream) -> {
      mapper.accept(element, downstream::push);
      return true;
    };
    return Gatherer.of(integrator);
  }

  @Test
  void limit_gatherer() {
    var result = IntStream.rangeClosed(1, 10)
        .boxed()
        .gather(limit(2))
        .toList();
    assertThat(result)
        .containsExactly(1, 2);

    var resultVariant = IntStream.rangeClosed(1, 10)
        .boxed()
        .gather(limitVariant(2))
        .toList();
    assertThat(resultVariant)
        .containsExactly(1, 2);
  }

  <T> Gatherer<T, ?, T> limit(long maxSize) {
    class Count {

      long left = maxSize;

      boolean integrate(T element, Downstream<? super T> downstream) {
        if (left > 0) {
          left--;
          return downstream.push(element);
        } else {
          return false;
        }
      }
    }
    return Gatherer.<T, Count, T>ofSequential(Count::new, Count::integrate);
  }

  <T> Gatherer<T, ?, T> limitVariant(long maxSize) {

    class Limit implements Gatherer<T, Limit.Count, T> {

      class Count {

        long left = maxSize;
      }

      @Override
      public Supplier<Count> initializer() {
        return Count::new;
      }

      @Override
      public Integrator<Count, T, T> integrator() {
        return (state, element, downstream) -> {
          if (state.left > 0) {
            state.left--;
            return downstream.push(element);
          } else {
            return false;
          }
        };
      }
    }

    return new Limit();
  }

}
