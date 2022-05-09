package com.example.java.playground.lib.streamex;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import one.util.streamex.EntryStream;
import org.junit.jupiter.api.Test;

class StreamExTest {

  @Test
  void map_concat() {
    var map1 = Map.of("1", "v1");
    var map2 = Map.of("2", "v2");
    var expected = Map.of(
        "1", "v1",
        "2", "v2");

    var map3 = EntryStream.of(map1)
        .append(EntryStream.of(map2))
        .toMap();
    assertThat(map3).isEqualTo(expected);
  }

}
