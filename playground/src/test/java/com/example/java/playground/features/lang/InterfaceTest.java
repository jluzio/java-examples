package com.example.java.playground.features.lang;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class InterfaceTest {

  interface SampleInterface {

    default int defaultFoo() {
      return 42;
    }
    static int staticFoo() {
      return 42;
    }
  }

  @Test
  void test() {
    var instance = new SampleInterface() {
    };
    log.info("{}", SampleInterface.staticFoo());
    log.info("{}", instance.defaultFoo());

    var instance2 = new SampleInterface() {
      @Override
      public int defaultFoo() {
        return 43;
      }
    };
    log.info("{}", instance2.defaultFoo());

  }
}
