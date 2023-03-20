package com.example.java.playground.features.collection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class CollectionsTest {

  @Test
  void enumeration_to_list() {
    var vector = new Vector<>(List.of("1", "2", "3"));
    Enumeration<String> enumeration = vector.elements();
    assertThat(Collections.list(enumeration))
        .isEqualTo(List.of("1", "2", "3"));
  }

}
