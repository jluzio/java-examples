package com.example.java.playground.features.stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

@Slf4j
class CollectorsTest {

  @Test
  void toMap_unique_keys() {
    var values = List.of(
        Pair.of("1", "1.1"),
        Pair.of("2", "2.1"),
        Pair.of("3", "3.1")
    );
    var valuesMap = values.stream().collect(Collectors.toMap(
        Pair::getKey,
        Pair::getValue
    ));
    log.info("{}", valuesMap);
    log.info("{}", valuesMap.getClass());
    log.info("{}", valuesMap.values());
    assertThat(valuesMap)
        .isEqualTo(Map.of(
            "1", "1.1",
            "2", "2.1",
            "3", "3.1"
        ));
  }

  @Test
  void toMap_duplicate_keys() {
    var values = List.of(
        Pair.of("1", "1.1"),
        Pair.of("1", "1.2"),
        Pair.of("2", "2.1"),
        Pair.of("3", "3.1"),
        Pair.of("2", "2.2"),
        Pair.of("1", "1.3"),
        Pair.of("2", "2.3")
    );
    assertThatThrownBy(() -> values.stream().collect(Collectors.toMap(
        Pair::getKey,
        Pair::getValue
    )))
        .isInstanceOf(IllegalStateException.class);

    var valuesMap = values.stream().collect(Collectors.toMap(
        Pair::getKey,
        Pair::getValue,
        (pair1, pair2) -> pair1
    ));
    log.info("{}", valuesMap);
    log.info("{}", valuesMap.getClass());
    log.info("{}", valuesMap.values());
    assertThat(valuesMap)
        .isEqualTo(Map.of(
            "1", "1.1",
            "2", "2.1",
            "3", "3.1"
        ));
  }

  @Test
  void groupBy_aggregate_functions() {
    List<String> values = List.of("a1234", "a123", "a12345", "b1", "b123");

    Map<String, Long> collectCount = values.stream()
        .collect(Collectors.groupingBy(
            v -> v.substring(0, 1),
            Collectors.counting()
        ));
    assertThat(collectCount)
        .isEqualTo(Map.of(
            "a", 3L,
            "b", 2L));

    Map<String, Double> collectAverage = values.stream()
        .collect(Collectors.groupingBy(
            v -> v.substring(0, 1),
            Collectors.averagingInt(v -> v.length() - 1)
        ));
    assertThat(collectAverage)
        .isEqualTo(Map.of(
            "a", 4d,
            "b", 2d));
  }

  @Test
  void groupBy_list() {
    List<String> values = List.of("a1234", "a123", "a12345", "b1", "b123");
    Map<Object, List<String>> collect = values.stream()
        .collect(Collectors.groupingBy(
            v -> v.substring(0, 1)
        ));
    assertThat(collect)
        .isEqualTo(Map.of(
            "a", List.of("a1234", "a123", "a12345"),
            "b", List.of("b1", "b123")));
  }

}
