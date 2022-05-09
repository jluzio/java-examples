package com.example.java.playground.lib.reactive.reactor;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.Map.Entry;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

class CollectionTest {

  @Test
  void map_concat() {
    var map1 = Map.of("1", "v1");
    var map2 = Map.of("2", "v2");
    var expected = Map.of(
        "1", "v1",
        "2", "v2");

    var map3 = Flux.fromIterable(map1.entrySet())
        .concatWith(Flux.fromIterable(map2.entrySet()))
        .collectMap(Entry::getKey, Entry::getValue)
        .block();
    assertThat(map3).isEqualTo(expected);
  }

}
