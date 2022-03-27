package com.example.java.playground.features.generics;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class BoundsTest {

  interface Initializer {

    void init();
  }

  @Test
  void multipleBounds() {
    class InitSupplier implements Initializer, Supplier<String> {

      @Override
      public void init() {
        log.debug("init");
      }

      @Override
      public String get() {
        log.debug("get");
        return "42";
      }
    }

    initSupplier(new InitSupplier());
  }

  @Test
  void lower_bounds() {
    var list = Lists.newArrayList("1234", "123", "12");
    Predicate<String> lengthPredicate = s -> s.length() > 3;
    Predicate<Object> objectPredicate = Predicates.alwaysFalse();
    list.removeIf(lengthPredicate);
    list.removeIf(objectPredicate);
    assertThat(list)
        .isEqualTo(List.of("123", "12"));
  }

  <T extends Supplier<String> & Initializer> void initSupplier(T supplier) {
    supplier.init();
    log.debug("get: {}", supplier.get());
  }

}
