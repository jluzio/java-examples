package com.example.java.playground.lib.guava;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.google.common.collect.MoreCollectors;
import java.util.List;
import org.junit.jupiter.api.Test;

class GuavaMiscTest {

  @Test
  void collectors() {
    List<String> listWith2Elements = List.of("1", "2");
    List<String> listWith1Element = List.of("1");

    assertThatThrownBy(
        () -> listWith2Elements.stream()
            .collect(MoreCollectors.onlyElement()))
        .isInstanceOf(IllegalArgumentException.class);

    String element = listWith1Element.stream()
        .collect(MoreCollectors.onlyElement());
    assertThat(element)
        .isEqualTo("1");
  }

}
