package com.example.java.playground.features;

import com.example.java.playground.AbstractTest;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;

public class CompletableFutureTest extends AbstractTest {

  @Test
  void test_when_complete() {
    runTask(2);
    runTask(0);
  }

  void runTask(int i) {
    System.out.printf("-- input: %s --%n", i);
    CompletableFuture
        .supplyAsync(() -> {
          return 16 / i;
        })
        .whenComplete((input, exception) -> {
          if (exception != null) {
            System.out.println("exception occurs");
            System.err.println(exception);
          } else {
            System.out.println("no exception, got result: " + input);
          }
        })
        .thenApply(input -> input * 3)
        .thenAccept(System.out::println);

  }
}
