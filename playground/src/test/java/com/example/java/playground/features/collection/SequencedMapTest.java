package com.example.java.playground.features.collection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SequencedMap;
import org.junit.jupiter.api.Test;

class SequencedMapTest {

  @Test
  void test() {
    SequencedMap<String, String> map = new LinkedHashMap<>();

    map.put("1", "v1");
    map.putFirst("0", "v0");
    map.putLast("2", "v2");
    assertThat(map).isEqualTo(Map.of(
        "0", "v0",
        "1", "v1",
        "2", "v2"
    ));
    assertThat(map.keySet().stream().toList())
        .isEqualTo(List.of("0", "1", "2"));
  }


}
