package com.example.java.challenges.codility.l4_counting_elements;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class PermCheckTest {

  Solution s = new Solution();

  @Test
  void test() {
    execute(new int[]{4, 1, 3, 2});
    execute(new int[]{4, 1, 3});
    execute(new int[]{2});
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
      // sort O(n * log n) and check if equal to expected
      java.util.Arrays.sort(A);
      for (int i = 0; i < A.length; i++) {
        if (A[i] != i + 1) {
          return 0;
        }
      }
      return 1;
    }
  }
}
