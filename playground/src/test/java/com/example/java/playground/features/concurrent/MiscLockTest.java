package com.example.java.playground.features.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class MiscLockTest {

  static class Locker {

    public synchronized void syncMethod1() {
      log.info("syncMethod1");
    }

    public synchronized void syncMethod2() {
      log.info("syncMethod2");
    }

    public synchronized void syncMethod1And2() {
      log.info("syncMethod1And2");
      syncMethod1();
      syncMethod2();
    }
  }

  Locker locker = new Locker();

  @Test
  void test() throws InterruptedException {
    locker.syncMethod1();
    locker.syncMethod2();
    locker.syncMethod1And2();

    ExecutorService executorService = Executors.newFixedThreadPool(5);
    var results = executorService.invokeAll(
        IntStream.rangeClosed(1, 20)
            .boxed()
            .map(this::processRun)
            .collect(Collectors.toList()));
    log.info("results: {}", results);
  }

  private Callable<Integer> processRun(Integer value) {
    return () -> {
      log.info("processRun: {}", value);
      locker.syncMethod1();
      log.info("processRun: {} :: syncMethod1", value);
      locker.syncMethod1();
      log.info("processRun: {} :: syncMethod2", value);
      return value;
    };
  }
}
