package com.example.java.playground.lib.reactive.reactor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.google.common.collect.Lists;
import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
class ReactorTest {

  @Test
  void simple() {
    Flux.just("Joe", "Bart", "Henry", "Nicole", "ABSLAJNFOAJNFOANFANSF")
        .subscribe(log::info);
  }

  @Test
  void publisher_and_block() {
    Publisher<String> publisher = Flux.interval(Duration.ofMillis(100))
        .limitRequest(10)
        .map(Object::toString);
    Flux<List<String>> bufferedPublisher = Flux.from(publisher)
        .buffer(3)
        .doOnNext(v -> log.info("buffered items: {}", v));

    // subscription 1
    bufferedPublisher
        .subscribe(v -> log.info("item consumer: {}", v));

    // subscription 2
    List<List<String>> values = bufferedPublisher
        .collectList()
        .block();
    log.info("values: {}", values);
  }

  @Test
  void error_handling() {
    List<String> values = Lists.newArrayList();
    Flux.just(1, 2, 0, 3, 4, 0, 5, 6)
        .map(i -> "100 / " + i + " = " + (100 / i)) //this triggers an error with 0
        .onErrorReturn("Divided by zero :(") // error handling example
        .doOnNext(log::info)
        .doOnNext(values::add)
        .subscribe();
    assertThat(values).isEqualTo(List.of("100 / 1 = 100", "100 / 2 = 50", "Divided by zero :("));

    List<String> values2 = Lists.newArrayList();
    Flux.just(1, 2, 0, 3, 4, 0, 5, 6)
        .flatMap(i -> Mono.fromSupplier(() -> "100 / " + i + " = " + (100 / i))
            .onErrorResume(e -> Mono.just("Divided by zero :("))
        )
        .doOnNext(log::info)
        .doOnNext(values2::add)
        .subscribe();
    assertThat(values2).isEqualTo(List.of(
        "100 / 1 = 100",
        "100 / 2 = 50",
        "Divided by zero :(",
        "100 / 3 = 33",
        "100 / 4 = 25",
        "Divided by zero :(",
        "100 / 5 = 20",
        "100 / 6 = 16"));

    Supplier<String> errorCallable = () -> {
      throw new RuntimeException("devious error");
    };
    Status result = Mono.fromSupplier(errorCallable)
        .map(v -> new Status("000", "Success"))
        .onErrorResume(e -> Mono.just(new Status("001", "Error: %s".formatted(e.getMessage()))))
        .block();
    log.info("result: {}", result);
    assertThat(result).isEqualTo(new Status("001", "Error: devious error"));

    try {
      Mono.fromSupplier(errorCallable)
          .map(v -> new Status("000", "Success"))
          .onErrorResume(e -> Mono.error(new BusinessException("Error: %s".formatted(e.getMessage()))))
          .block();
      fail("expected exception");
    } catch (Exception e) {
      assertThat(e).isInstanceOf(BusinessException.class).hasMessage("Error: devious error");
    }

  }

}
