package com.example.java.playground.features.collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

@Slf4j
class CollectorToMapTest {

  @Test
  void unique_keys() {
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
  void duplicate_keys() {
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
  void ordering() {
    var keys = IntStream.iterate(10, v -> v - 1)
        .limit(10)
        .mapToObj(String::valueOf)
        .toList();

    var values = keys.stream()
        .flatMap(k -> IntStream.rangeClosed(1, 10)
            .mapToObj(v -> Pair.of(k, "%s.%s".formatted(k, v))))
        .toList();
    log.info("values: {}", values);

    var valuesMap = values.stream().collect(Collectors.toMap(
        Pair::getKey,
        Pair::getValue,
        (pair1, pair2) -> pair1
    ));

    log.info("{}", valuesMap);
    log.info("{}", valuesMap.getClass());

    var valuesMapValues = new ArrayList<>(valuesMap.values());
    log.info("valuesMapValues: {}", valuesMapValues);

    var orderedExpectedValues = keys.stream()
        .map("%s.1"::formatted)
        .toList();
    // values are not ordered with the insert order (since it uses a HashMap)
    assertThat(valuesMapValues)
        .isNotEqualTo(orderedExpectedValues);
  }

  @Test
  void ordering_using_reduce() {
    var keys = IntStream.iterate(10, v -> v - 1)
        .limit(10)
        .mapToObj(String::valueOf)
        .toList();

    var values = keys.stream()
        .flatMap(k -> IntStream.rangeClosed(1, 10)
            .mapToObj(v -> Pair.of(k, "%s.%s".formatted(k, v))))
        .toList();
    log.info("values: {}", values);

    var valuesMap = values.stream().reduce(
        new LinkedHashMap<String, String>(),
        (acc, value) -> {
          acc.computeIfAbsent(value.getKey(), k -> value.getValue());
          return acc;
        },
        (acc1, acc2) -> null
    );

    log.info("{}", valuesMap);
    log.info("{}", valuesMap.getClass());

    var valuesMapValues = new ArrayList<>(valuesMap.values());
    log.info("valuesMapValues: {}", valuesMapValues);

    var orderedExpectedValues = keys.stream()
        .map("%s.1"::formatted)
        .toList();
    // values are not ordered with the insert order (since it uses a HashMap)
    assertThat(valuesMapValues)
        .isEqualTo(orderedExpectedValues);
  }

}
