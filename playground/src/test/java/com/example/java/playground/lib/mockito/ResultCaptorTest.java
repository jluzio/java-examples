package com.example.java.playground.lib.mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ResultCaptorTest {

  static class NumberSupplier implements Supplier<Integer> {

    AtomicInteger count = new AtomicInteger(0);

    @Override
    public Integer get() {
      return count.incrementAndGet();
    }
  }

  @RequiredArgsConstructor
  static class NumberConsumer {

    private Supplier<Integer> numberSupplier;

    public String consume() {
      return "number=%d".formatted(numberSupplier.get());
    }
  }

  @InjectMocks
  NumberConsumer numberConsumer;
  @Spy
  NumberSupplier numberSupplier;

  @Test
  void test() {
    var captor = new ResultCaptor<Integer>();
    doAnswer(captor).when(numberSupplier).get();

    var output1 = numberConsumer.consume();
    assertThat(output1).isEqualTo("number=1");
    assertThat(captor.getValue()).isEqualTo(1);
    assertThat(captor.getAllValues()).containsExactly(1);

    var output2 = numberConsumer.consume();
    assertThat(output2).isEqualTo("number=2");
    assertThat(captor.getValue()).isEqualTo(2);
    assertThat(captor.getAllValues()).containsExactly(1, 2);
  }
}
