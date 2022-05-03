package com.example.java.playground.features.lang;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class AnonymousBlockTest {

  @Test
  void map_init() {
    var map = new HashMap<String, String>() {
      {
        this.put("key1", "val1");
        this.put("key2", "val2");
      }
    };
    assertThat(map)
        .isEqualTo(Map.of(
            "key1", "val1",
            "key2", "val2"));
  }

}
