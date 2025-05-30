package com.example.java.playground.features.collection;

import static java.util.Map.entry;
import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.AbstractMap.SimpleEntry;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
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

    // WARNING: this creates an anonymous class, ie more work/memory for the classloader
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

  @Test
  void filter_out_null_values_with_stream_entries() {
    var map = Stream.of(
            entry("k1", ofNullable("v1")),
            entry("k2", ofNullable("v2")),
            entry("k3", ofNullable(null)),
            entry("k4", Optional.empty())
        ).filter(e -> e.getValue().isPresent())
        .collect(Collectors.toMap(Entry::getKey, e -> e.getValue().get()));
    assertThat(map)
        .isEqualTo(Map.of(
            "k1", "v1",
            "k2", "v2"
        ));
  }

  @Test
  void null_values_with_simple_entry() {
    var map = Stream.of(
            entry("k1", "v1"),
            entry("k2", "v2"),
            new SimpleEntry<>("k3", null)
        ).collect(HashMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), HashMap::putAll);
    var expectedMap = new HashMap<String, String>();
    expectedMap.put("k1", "v1");
    expectedMap.put("k2", "v2");
    expectedMap.put("k3", null);
    assertThat(map)
        .isEqualTo(expectedMap);
  }

  @Test
  void map_compute() {
    var map = new HashMap<String, String>();
    String k1 = "key1";

    map.compute(k1, (k, v) ->  (v == null) ? "init_value" : "previous: " + v);
    assertThat(map)
        .hasFieldOrPropertyWithValue(k1, "init_value");

    map.compute(k1, (k, v) ->  (v == null) ? "init_value" : "previous: " + v);
    assertThat(map)
        .hasFieldOrPropertyWithValue(k1, "previous: init_value");

    String k2 = "key2";
    map.computeIfAbsent(k2, "no value for key %s"::formatted);
    assertThat(map)
        .hasFieldOrPropertyWithValue(k2, "no value for key key2");
    map.put(k2, "existing value");
    map.computeIfAbsent(k2, "no value for key %s"::formatted);
    assertThat(map)
        .hasFieldOrPropertyWithValue(k2, "existing value");

    String k3 = "key3";
    map.computeIfPresent(k3, "key %s has value"::formatted);
    assertThat(map)
        .doesNotContainKey(k3);
    map.put(k3, "existing value");
    map.computeIfPresent(k3, "key %s has value"::formatted);
    assertThat(map)
        .hasFieldOrPropertyWithValue(k3, "key key3 has value");
  }

  @Test
  void map_merge() {
    var map = new HashMap<String, Integer>();
    String k1 = "key1";

    map.merge(k1, 1, Integer::sum);
    assertThat(map)
        .hasFieldOrPropertyWithValue(k1, 1);

    map.merge(k1, 1, Integer::sum);
    assertThat(map)
        .hasFieldOrPropertyWithValue(k1, 2);
  }

  @Test
  void map_others() {
    var map = new HashMap<String, Integer>();
    String k1 = "key1";

    map.putIfAbsent(k1, 1);
    assertThat(map)
        .hasFieldOrPropertyWithValue(k1, 1);
    map.putIfAbsent(k1, 2);
    assertThat(map)
        .hasFieldOrPropertyWithValue(k1, 1);

    map.remove(k1);
    assertThat(map.getOrDefault(k1, 3))
        .isEqualTo(3);
  }

}
