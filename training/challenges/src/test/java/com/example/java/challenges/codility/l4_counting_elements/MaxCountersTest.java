package com.example.java.challenges.codility.l4_counting_elements;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class MaxCountersTest {

  Solution s = new Solution();

  @Test
  void test() {
    execute(5, new int[]{3, 4, 4, 6, 1, 4, 4});
  }

  void execute(int N, int[] A) {
    var params = "%s | %s".formatted(N, Arrays.toString(A));
    System.out.printf("%s :: %s%n",
        Arrays.toString(s.solution(N, A)),
        params
    );
  }

  class Solution {

    public int[] solution(int N, int[] A) {
      // Implement your solution here
      int[] counters = new int[N];
      int min = 0, max = 0;
      for (int i = 0, x; i < A.length; i++) {
        x = A[i];
        if (x == N + 1) {
          min = max;
        } else {
          int counter = counters[x - 1] = Math.max(counters[x - 1], min) + 1;
          max = Math.max(counter, max);
        }
      }
      for (int i = 0; i < counters.length; i++) {
        counters[i] = Math.max(counters[i], min);
      }
      return counters;
    }
  }
}
