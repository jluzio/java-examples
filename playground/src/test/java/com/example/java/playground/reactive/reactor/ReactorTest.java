package com.example.java.playground.reactive.reactor;

import java.time.Duration;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

@Slf4j
class ReactorTest {

  @Test
  void simple() {
    Flux.just("Joe", "Bart", "Henry", "Nicole", "ABSLAJNFOAJNFOANFANSF")
        .subscribe(log::info);
  }


  @Test
  void publisher_and_block() {
    Publisher<String> publisher = Flux.interval(Duration.ofMillis(5))
        .limitRequest(10)
        .map(Object::toString);
    List<List<String>> values = Flux.from(publisher)
        .buffer(3)
        .collectList()
        .block();
    log.info("values: {}", values);
  }

}
