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

}
