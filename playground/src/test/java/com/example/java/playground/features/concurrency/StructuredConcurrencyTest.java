package com.example.java.playground.features.concurrency;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.concurrent.StructuredTaskScope.Subtask.State;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

@Slf4j
class StructuredConcurrencyTest {

  @Test
  void test_ShutdownOnFailure_success() throws InterruptedException {
    try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {

      Subtask<String> accountDetailsSubtask = scope.fork(valueTask("accountDetails"));
      Subtask<String> linkedAccountsSubtask = scope.fork(delayedValueTask("linkedAccounts"));
      Subtask<String> userDetailsSubtask = scope.fork(delayedValueTask("userDetails"));

      scope.join()  // Join all subtasks
          .throwIfFailed(e -> new IllegalArgumentException("Bad User"));

      // The subtasks have completed by now so process the result
      log.info("{} | {} | {}",
          accountDetailsSubtask.get(),
          linkedAccountsSubtask.get(),
          userDetailsSubtask.get()
      );
      logSubtask("accountDetailsSubtask", accountDetailsSubtask);
      logSubtask("linkedAccountsSubtask", linkedAccountsSubtask);
      logSubtask("userDetailsSubtask", userDetailsSubtask);
      assertThat(accountDetailsSubtask.state()).isEqualTo(State.SUCCESS);
      assertThat(linkedAccountsSubtask.state()).isEqualTo(State.SUCCESS);
      assertThat(userDetailsSubtask.state()).isEqualTo(State.SUCCESS);
    }
  }

  @Test
  void test_ShutdownOnFailure_failure() throws InterruptedException {
    try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {

      Subtask<String> accountDetailsSubtask = scope.fork(valueTask("accountDetails"));
      Subtask<String> linkedAccountsSubtask = scope.fork(delayedValueTask("linkedAccounts"));
      Subtask<String> userDetailsSubtask = scope.fork(exceptionTask());

      scope.join();  // Join all subtasks
      assertThatThrownBy(() -> scope.throwIfFailed(e -> new IllegalArgumentException("Bad User")))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage("Bad User");

      logSubtask("accountDetailsSubtask", accountDetailsSubtask);
      logSubtask("linkedAccountsSubtask", linkedAccountsSubtask);
      logSubtask("userDetailsSubtask", userDetailsSubtask);
      assertThat(accountDetailsSubtask.state()).isEqualTo(State.SUCCESS);
      assertThat(linkedAccountsSubtask.state()).isEqualTo(State.UNAVAILABLE);
      assertThat(userDetailsSubtask.state()).isEqualTo(State.FAILED);
    }
  }

  @Test
  void test_ShutdownOnSuccess_success() throws InterruptedException, ExecutionException {
    try (var scope = new StructuredTaskScope.ShutdownOnSuccess<>()) {

      Subtask<String> subtask1 = scope.fork(valueTask("subtask1"));
      Subtask<String> subtask2 = scope.fork(delayedValueTask("subtask2"));
      Subtask<String> subtask3 = scope.fork(delayedValueTask("subtask3"));

      var result = scope.join()  // Join all subtasks
          .result();
      assertThat(result).isEqualTo("subtask1");

      logSubtask("task1", subtask1);
      logSubtask("task2", subtask2);
      logSubtask("task3", subtask3);
      assertThat(subtask1.state()).isEqualTo(State.SUCCESS);
      assertThat(subtask2.state()).isEqualTo(State.UNAVAILABLE);
      assertThat(subtask3.state()).isEqualTo(State.UNAVAILABLE);
    }
  }

  private <T> void logSubtask(String name, Subtask<T> subtask) {
//    log.info("{}: {}", name, subtask);
    var data = Stream.of(
        entry("state", subtask.state()),
        entry("get", subtask.state() == State.SUCCESS ? subtask.get() : ""),
        entry("exception", subtask.state() == State.FAILED ? subtask.exception() : ""),
        entry("task", subtask.task())
    ).reduce(
        new LinkedHashMap<String, Object>(),
        (acc, value) -> {
          acc.computeIfAbsent(value.getKey(), k -> value.getValue());
          return acc;
        },
        (acc1, acc2) -> null
    );
    log.info("{}.data: {}", name, data);
  }

  private Callable<String> valueTask(String value) {
    return () -> value;
  }

  private Callable<String> delayedValueTask(String value) {
    return () -> Mono.just(value)
        .delayElement(Duration.ofMillis(200))
        .block();
  }

  private Callable<String> exceptionTask() {
    return () -> {
      throw new IOException("data issues");
    };
  }

}
