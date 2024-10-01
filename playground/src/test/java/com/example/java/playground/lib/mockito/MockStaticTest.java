package com.example.java.playground.lib.mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;

class MockStaticTest {

  static class Greeter {

    public static String hello() {
      return "Hello!";
    }
  }

  record Message(String value) {

    public static Message instance() {
      return new Message("42");
    }
  }

  @Test
  void test_basic() {
    assertThat(Greeter.hello())
        .isEqualTo("Hello!");

    try (var mockStatic = Mockito.mockStatic(Greeter.class)) {
      mockStatic
          .when(Greeter::hello)
          .thenReturn("Hello from Mock!");
    }

    assertThat(Greeter.hello())
        .isEqualTo("Hello!");
  }

  @Test
  void test_instance() {
    assertThat(Message.instance().value())
        .isEqualTo("42");

    try (var mockStatic = Mockito.mockStatic(Message.class)) {
      mockStatic
          .when(Message::instance)
          .thenReturn(new Message("24"));

      assertThat(Message.instance().value())
          .isEqualTo("24");
    }

    assertThat(Message.instance().value())
        .isEqualTo("42");
  }

  @Test
  void test_CompletableFuture() throws ExecutionException, InterruptedException {
    var expectedCompletableFuture = CompletableFuture.completedFuture("42");
    Supplier<String> stringSupplier = () -> "1";

    try (var mockStatic = Mockito.mockStatic(CompletableFuture.class, InvocationOnMock::callRealMethod)) {
      mockStatic
          .when(() -> CompletableFuture.supplyAsync(any()))
          .thenReturn(expectedCompletableFuture);

      CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(stringSupplier);
      assertThat(completableFuture)
          .isNotNull();
      assertThat(completableFuture.get())
          .isEqualTo("42");
    }
  }

}
