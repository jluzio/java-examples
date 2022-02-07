package com.example.java.playground.features.collection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.EnumMap;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class MapTest {
  enum Level {
    LOW,
    MEDIUM,
    HIGH
  }


  @Test
  void enumMap() {
    EnumMap<Level, Integer> map = new EnumMap<>(Level.class);
    map.put(Level.LOW, 1);
    map.put(Level.MEDIUM, 2);
    map.put(Level.HIGH, 3);
    log.info("map: {}", map);
    assertThat(map).isNotNull();
  }

}
