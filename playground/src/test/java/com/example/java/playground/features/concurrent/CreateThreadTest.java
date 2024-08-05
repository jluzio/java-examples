package com.example.java.playground.features.concurrent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

@Log4j2
class CreateThreadTest {

  @Test
  void thread() throws InterruptedException {
    var runFlag = new AtomicBoolean();

    var thread = new Thread(() -> {
      log.info("run()");
      runFlag.set(true);
    });
    thread.start();
    thread.join();

    assertThat(runFlag).isTrue();
  }

  @Test
  void executor_execute() {
    try (ExecutorService executorService = Executors.newSingleThreadExecutor()) {
      var runFlag = new AtomicBoolean();

      executorService.execute(() -> {
        log.info("run()");
        runFlag.set(true);
      });
      await()
          .untilTrue(runFlag);

      assertThat(runFlag).isTrue();
    }
  }

  @Test
  void executor_submit() throws ExecutionException, InterruptedException {
    try (ExecutorService executorService = Executors.newSingleThreadExecutor()) {
      var runFlag = new AtomicBoolean();

      var future = executorService.submit(() -> {
        log.info("run()");
        runFlag.set(true);
      });
      future.get();

      assertThat(runFlag).isTrue();
    }
  }

  @Test
  void scheduledThreadPoolExecutor() throws ExecutionException, InterruptedException {
    try (ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1)) {
      var runFlag = new AtomicBoolean();

      var future = executorService.schedule(
          () -> {
            log.info("run()");
            runFlag.set(true);
          },
          50, TimeUnit.MILLISECONDS);
      future.get();

      assertThat(runFlag).isTrue();
    }
  }

  @Test
  void completableFuture_supplyAsync() throws ExecutionException, InterruptedException {
    try (ExecutorService executorService = Executors.newSingleThreadExecutor()) {
      var runFlag = new AtomicBoolean();

      Supplier<String> supplier = () -> {
        log.info("supplyAsync()");
        runFlag.set(true);
        return "42";
      };
      // can omit executorService for a default executor
      var future = CompletableFuture.supplyAsync(supplier, executorService);
      future.get();

      assertThat(runFlag).isTrue();
    }
  }

  @Test
  void timerTask() {
    var runFlag = new AtomicBoolean();

    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
        log.info("run()");
        runFlag.set(true);
      }
    };
    timer.schedule(timerTask, 50);

    await()
        .untilTrue(runFlag);

    assertThat(runFlag).isTrue();
  }

}
