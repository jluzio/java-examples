package com.example.java.playground.lib.reactive.reactor;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
class ReactiveMultiThreadTest {

  Scheduler valuesScheduler = Schedulers.newParallel("values-worker", 5);

  @Test
  void test_multi_thread() {
    List<String> values = Flux.range(0, 20)
        .publishOn(Schedulers.boundedElastic())
        .log()
        .flatMap(this::processValue)
        .publishOn(Schedulers.boundedElastic())
        .log()
        .collectList()
        .block();
    List<String> valuesOrdered = IntStream.range(0, 20)
        .boxed()
        .map(this::valueMapper)
        .collect(Collectors.toList());
    assertThat(values)
        .hasSize(20)
        .isNotEqualTo(valuesOrdered)
        .containsAll(valuesOrdered);
  }

  private Mono<String> processValue(Integer value) {
    return Mono.just(value)
        .delayElement(Duration.ofMillis(ThreadLocalRandom.current().nextInt(0, 50)))
        .publishOn(valuesScheduler)
        .map(this::valueMapper)
        .doOnNext(v -> log.info("processValue: {}", v));
  }

  private String valueMapper(Integer value) {
    return "res-%s".formatted(value);
  }

}
