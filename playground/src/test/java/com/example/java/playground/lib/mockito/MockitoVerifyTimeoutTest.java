package com.example.java.playground.lib.mockito;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class MockitoVerifyTimeoutTest {

  @Mock
  Runnable signal;

  @Test
  void timeout_testing() {
    Runnable task = () -> {
      Mono.delay(Duration.ofMillis(50L))
          .doOnNext(_ -> signal.run())
          .subscribe();
    };

    task.run();
    verify(signal, timeout(100L)).run();

    clearInvocations(signal);
    task.run();
    assertThatThrownBy(() -> verify(signal, timeout(20L)).run())
        .isInstanceOf(AssertionError.class);
  }

}
