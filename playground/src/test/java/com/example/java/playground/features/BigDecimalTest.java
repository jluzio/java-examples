package com.example.java.playground.features;

import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

@Slf4j
public class BigDecimalTest {

  @Test
  void test() {
    BigDecimal n1 = new BigDecimal("10.01");
    BigDecimal n2 = new BigDecimal("10.00");
    BigDecimal n3 = new BigDecimal("10");
    BigDecimal n4 = new BigDecimal("0");

    Flux.just(n1, n2, n3, n4)
        .doOnNext(v -> {
          log.info("{} : {} : {}", v, v.unscaledValue(), v.stripTrailingZeros().scale());
        })
        .doOnNext(v -> {
          var y = v.multiply(BigDecimal.valueOf(-1));
          log.info("{} : {} : {}", y, y.unscaledValue(), y.stripTrailingZeros().scale());
        })
    .subscribe()
    ;

  }

}
