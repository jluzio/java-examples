package com.example.java.playground.reactive.reactor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ReactiveMultiThreadScenarioTest {

  @Mock
  Function<Integer, Mono<String>> service;

  @Test
  void test_multi_thread() {
    int count = 3;
    List<Integer> baseDurations = List.of(50, 10, 30, 20, 40);
//    int durationMod = 1;
    int durationMod = 100;

    IntStream.range(0, count).forEach(v ->
        when(service.apply(eq(v)))
            .thenReturn(processValue(v, Duration.ofMillis(baseDurations.get(v) * durationMod)))
    );

    Flux<Integer> flux = Flux.range(0, count);
//    Flux<Integer> flux = Flux.fromIterable(
//        IntStream.range(0, count).boxed().collect(Collectors.toList()));

    List<String> values = flux
        .log()
        .flatMap(service::apply)
        .publishOn(Schedulers.boundedElastic())
        .log()
        .collectList()
        .block();
    List<String> valuesOrdered = IntStream.range(0, count)
        .boxed()
        .map(this::valueMapper)
        .collect(Collectors.toList());
    assertThat(values)
        .hasSize(count)
        .isNotEqualTo(valuesOrdered)
        .containsAll(valuesOrdered);
  }

  private Mono<String> processValue(Integer value, Duration duration) {
    return Mono.just(value)
        .delayElement(duration)
        .map(this::valueMapper)
        ;
  }

  private String valueMapper(Integer value) {
    return "res-%s".formatted(value);
  }

}
