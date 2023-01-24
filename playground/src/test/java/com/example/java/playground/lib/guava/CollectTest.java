package com.example.java.playground.lib.guava;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Streams;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class CollectTest {

  @Test
  void map_builder() {
    var map = ImmutableMap.builder()
        .put("k1", "v1")
        .put("k2", "v2")
        .put("k3", "v3")
        .put("k4", "v4")
        .put("k5", "v5")
        .build();
    assertThat(new ArrayList<>(map.keySet()))
        .isEqualTo(List.of("k1", "k2", "k3", "k4", "k5"));

    var map2 = ImmutableMap.of(
        "k1", "v1",
        "k2", "v2",
        "k3", "v3",
        "k4", "v4",
        "k5", "v5");
    assertThat(new ArrayList<>(map2.keySet()))
        .isEqualTo(List.of("k1", "k2", "k3", "k4", "k5"));

    var mapDefault = Map.of(
        "k1", "v1",
        "k2", "v2",
        "k3", "v3",
        "k4", "v4",
        "k5", "v5");
    assertThat(new ArrayList<>(mapDefault.keySet()))
        .isEqualTo(List.of("k1", "k2", "k3", "k4", "k5"));
  }

  @Test
  void forEachPair() {
    var output = new ArrayList<String>();
    var values = List.of("a", "b", "c");
    Streams.forEachPair(
        values.stream(),
        IntStream.range(0, values.size()).boxed(),
        (v, i) -> output.add("%s:%s".formatted(i, v)));
    log.debug("output: {}", output);
    assertThat(output).contains("0:a", "1:b", "2:c");
  }

}
