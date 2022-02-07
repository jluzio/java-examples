package com.example.java.playground.features.concurrency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

@Slf4j
class ExecutorTest {

  @Test
  void executor() {
    var executorService = Executors.newSingleThreadExecutor();
    executorService.execute(() -> log.info("executor"));
  }

  @Test
  void executorService() throws ExecutionException, InterruptedException, TimeoutException {
    var executorService = Executors.newCachedThreadPool();
    Runnable runnable = () -> log.info("runnable");
    Supplier<Integer> callable = () -> {
      log.info("callable");
      return 42;
    };

    Future<?> futureRunnable = executorService.submit(runnable);
    assertThat(futureRunnable)
        .succeedsWithin(Duration.ofMillis(1));

    Future<Integer> futureCallable = executorService.submit(callable::get);
    assertThat(futureCallable)
        .succeedsWithin(Duration.ofMillis(1))
        .isEqualTo(42);

    Future<Integer> futureDelayedCallable = executorService.submit(() ->
        Mono.delay(Duration.ofMillis(200))
            .map(ignored -> callable.get())
            .block());
    assertThatThrownBy(() -> futureDelayedCallable.get(100, TimeUnit.MILLISECONDS))
        .isInstanceOf(TimeoutException.class);
  }

}
