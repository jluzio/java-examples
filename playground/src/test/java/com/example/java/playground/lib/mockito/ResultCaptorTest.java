package com.example.java.playground.lib.mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;

import java.util.Random;
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

  static class NumberSupplier implements Supplier<Long> {

    @Override
    public Long get() {
      return new Random().nextLong();
    }
  }

  @RequiredArgsConstructor
  static class NumberConsumer {
    private Supplier<Long> numberSupplier;

    public String defineNumber() {
      return numberSupplier.get() % 2 == 0 ? "even" : "odd";
    }
  }

  @InjectMocks
  NumberConsumer numberConsumer;
  @Spy
  NumberSupplier numberSupplier;

  @Test
  void test() {
    var captor = new ResultCaptor<Long>();
    doAnswer(captor).when(numberSupplier).get();
    var output = numberConsumer.defineNumber();
    var number = captor.getValue();
    assertThat(output)
        .isEqualTo(number % 2 == 0 ? "even" : "odd");
  }
}
