package com.example.java.playground.features.collection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

  @Test
  void map_concat() {
    var map1 = Map.of("1", "v1");
    var map2 = Map.of("2", "v2");
    var expected = Map.of(
        "1", "v1",
        "2", "v2");

    var map3_1 = new HashMap<>(map1);
    map3_1.putAll(map2);
    assertThat(map3_1)
        .containsAllEntriesOf(expected);

    var map3_2 = new HashMap<>() {
      {
        putAll(map1);
        putAll(map2);
      }
    };
    assertThat(map3_2)
        .containsAllEntriesOf(expected);

    var map3_3 = Stream.of(map1, map2)
        .flatMap(m -> m.entrySet().stream())
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    assertThat(map3_3)
        .containsAllEntriesOf(expected);

    // See also other libs, for ex StreamEx
  }

}
