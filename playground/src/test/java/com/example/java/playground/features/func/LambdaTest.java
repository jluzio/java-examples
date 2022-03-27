package com.example.java.playground.features.func;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class LambdaTest {

  @Test
  void test() {
    Consumer<Object> logIt = o -> log.info("o: {}", o);
    Function<Object, String> getInfo = o -> "%s{%s}".formatted(o.getClass().getSimpleName(), o);

    Stream.of(1, 2, 3).forEach(v -> log.info("v: {}", v));
    Stream.of(1, 2, 3).forEach(logIt);

    // error
//        Stream.of(1, 2, 3).forEach(getInfo);
    log.info("map: {}", Stream.of(1, 2, 3).map(getInfo).collect(Collectors.toList()));

  }
}
