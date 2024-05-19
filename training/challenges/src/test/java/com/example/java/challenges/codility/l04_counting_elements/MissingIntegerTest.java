package com.example.java.challenges.codility.l04_counting_elements;

import java.util.Arrays;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class MissingIntegerTest {

  Solution s = new Solution();

  @Test
  void test() {
    execute(new int[]{1, 3, 6, 4, 1, 2});
    execute(new int[]{1, 2, 3});
    execute(new int[]{-1, -3});
    execute(
        IntStream.rangeClosed(1, 100_000)
            .filter(v -> v != 99999)
            .toArray()
    );
  }

  void execute(int[] A) {
    var params = "%s".formatted(Arrays.toString(A));
    System.out.printf("%s :: %s%n",
        s.solution(A),
        params
    );
  }

  class Solution {

    public int solution(int[] A) {
      // sort O(n * log n), and then check for first missing O(n)
      Arrays.sort(A);
      int expected = 1;
      for (int v : A) {
        if (v > expected) {
          break;
        } else if (v == expected) {
          expected++;
        }
      }
      return expected;
    }
  }
}
