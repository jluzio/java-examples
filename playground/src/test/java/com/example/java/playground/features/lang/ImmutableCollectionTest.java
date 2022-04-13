package com.example.java.playground.features.lang;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;

class ImmutableCollectionTest {

  @Test
  void list_search() {
    var data = List.of(1, 2, 5);
    assertThat(data.contains(1))
        .isTrue();
    assertThat(data.contains(3))
        .isFalse();
    assertThatThrownBy(() -> data.contains(null))
        .isInstanceOf(NullPointerException.class);

    assertThat(data.stream().anyMatch(Predicate.isEqual(1)))
        .isTrue();
    assertThat(data.stream().anyMatch(Predicate.isEqual(null)))
        .isFalse();
  }

}
