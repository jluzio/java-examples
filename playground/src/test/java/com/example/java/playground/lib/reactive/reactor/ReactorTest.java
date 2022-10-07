package com.example.java.playground.lib.reactive.reactor;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

@Slf4j
class ReactorTest {

  @Test
  void basic() {
    Flux<String> publisher = Flux.just("Joe", "Bart", "Henry", "Nicole");
    Publisher<String> standardPublisher = publisher;

    publisher.subscribe(log::info);

    StepVerifier.create(publisher)
        .expectNextCount(4)
        .verifyComplete();
  }

  @Test
  void basic_testPublisher() {
    var publisher = TestPublisher.create();

    StepVerifier.create(publisher)
        .then(() -> publisher.emit("Joe", "Bart", "Henry", "Nicole"))
        .expectNextCount(4)
        .verifyComplete();
  }

  @Test
  void exceptions() {
    Flux<Integer> fluxToTest = Flux.just(1, 2, 3, 4)
        .concatWith(Mono.error(new ArithmeticException("Error occurred!")));

    StepVerifier.create(fluxToTest)
        .expectNext(1)
        .expectNext(2)
        .expectNext(3)
        .expectNext(4)
        .expectErrorMatches(throwable -> throwable instanceof ArithmeticException &&
            throwable.getMessage().equals("Error occurred!"))
        .verify();
  }

  @Test
  void virtual_time() {
    Supplier<Flux<String>> publisherSupplier = () -> Flux
        .interval(Duration.ofSeconds(10))
        .take(5)
        .map(Objects::toString);

    StepVerifier.withVirtualTime(publisherSupplier)
        .thenAwait(Duration.ofSeconds(30))
        .expectNextCount(3)
        .thenAwait(Duration.ofSeconds(20))
        .expectNextCount(2)
        .verifyComplete();
  }

  @Test
  void publisher_and_block() {
    Publisher<String> publisher = Flux.interval(Duration.ofMillis(100))
        .take(10)
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
  void subscribe_long_running_task() {
    Runnable runnable = () -> {
      log.debug("publisher<start>");
      Mono.delay(Duration.ofMillis(100)).block();
      log.debug("publisher<end>");
    };
    var publisher = Mono.fromRunnable(runnable)
        .subscribeOn(Schedulers.boundedElastic());

    log.debug("start");
    StepVerifier.create(publisher)
        .expectComplete()
        .verify(Duration.ofMillis(150));
    log.debug("end");
  }

}
