package com.example.java.playground.features.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Test;

class ComparatorTest {

  record Data(int id, String name, int order) {

  }

  @Test
  void comparator_comparing() {
    var dataList = List.of(
        new Data(1, "name-1", 3),
        new Data(2, "name-2", 1),
        new Data(3, "name-3", 2)
    );
    assertThat(dataList.stream()
        .sorted(Comparator.comparingInt(Data::order)))
        .map(Data::id)
        .containsExactly(2, 3, 1);
    assertThat(dataList.stream()
        .sorted(Comparator.comparing(Data::name).reversed()))
        .map(Data::id)
        .containsExactly(3, 2, 1);
  }

}
