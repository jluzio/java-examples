package com.example.java.challenges.sorting;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class QuicksortTest {

  Quicksort sort = new Quicksort();

  @Test
  void test() {
    int[] values = new int[]{1, 4, 2, 3, 5, 5, 10, 1};

    var expected = Arrays.copyOf(values, values.length);
    Arrays.sort(expected);

    sort.quicksort(values, 0, values.length - 1);
    assertThat(values)
        .containsExactly(expected);
  }

}
