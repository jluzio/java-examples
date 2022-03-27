package com.example.java.playground.features.collection;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class ForEachTest {

  @Test
  void test() {
    Stream.of(1, 2, 3).forEach(v -> log.info("v: {}", v));
    Arrays.asList(1, 2, 3).forEach(v -> log.info("v: {}", v));
    List.of(1, 2, 3).forEach(v -> log.info("v: {}", v));
  }
}
