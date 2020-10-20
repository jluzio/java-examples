package com.example.java.playground.features;

import com.example.java.playground.AbstractTest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class ForEachTest extends AbstractTest {

  @Test
  void test() {
    Stream.of(1, 2, 3).forEach(v -> log.info("v: {}", v));
    Arrays.asList(1, 2, 3).forEach(v -> log.info("v: {}", v));
    List.of(1, 2, 3).forEach(v -> log.info("v: {}", v));
  }
}
