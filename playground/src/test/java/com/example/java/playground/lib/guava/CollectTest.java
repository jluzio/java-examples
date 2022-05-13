package com.example.java.playground.lib.guava;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

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
        .isNotEqualTo(List.of("k1", "k2", "k3", "k4", "k5"));
  }

}
