package com.example.java.playground.features.concurrent;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class CountDownLatchTest {

  @Test
  void whenDoingLotsOfThreadsInParallel_thenStartThemAtTheSameTime()
      throws InterruptedException {

    List<String> outputScraper = Collections.synchronizedList(new ArrayList<>());
    CountDownLatch readyThreadCounter = new CountDownLatch(5);
    CountDownLatch callingThreadBlocker = new CountDownLatch(1);
    CountDownLatch completedThreadCounter = new CountDownLatch(5);
    List<Thread> workers = Stream
        .generate(() -> new Thread(new WaitingWorker(
            outputScraper, readyThreadCounter, callingThreadBlocker, completedThreadCounter)))
        .limit(5)
        .toList();

    workers.forEach(Thread::start);
    readyThreadCounter.await();
    outputScraper.add("Workers ready");
    callingThreadBlocker.countDown();
    completedThreadCounter.await();
    outputScraper.add("Workers complete");

    assertThat(outputScraper)
        .containsExactly(
            "Workers ready",
            "Counted down",
            "Counted down",
            "Counted down",
            "Counted down",
            "Counted down",
            "Workers complete"
        );
  }

  @RequiredArgsConstructor
  static class WaitingWorker implements Runnable {

    private final List<String> outputScraper;
    private final CountDownLatch readyThreadCounter;
    private final CountDownLatch callingThreadBlocker;
    private final CountDownLatch completedThreadCounter;

    @Override
    public void run() {
      readyThreadCounter.countDown();
      try {
        callingThreadBlocker.await();
        doSomeWork();
        outputScraper.add("Counted down");
      } catch (InterruptedException e) {
        log.error("Interrupted", e);
      } finally {
        completedThreadCounter.countDown();
      }
    }

    private void doSomeWork() {
      log.info("doSomeWork");
    }
  }
}
