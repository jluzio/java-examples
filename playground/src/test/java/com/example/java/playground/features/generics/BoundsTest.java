package com.example.java.playground.features.generics;

import java.util.concurrent.Callable;
import java.util.function.Function;
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

  <T extends Supplier<String> & Initializer> void initSupplier(T supplier) {
    supplier.init();
    log.debug("get: {}", supplier.get());
  }

}
