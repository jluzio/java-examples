package com.example.java.challenges.codility.l2_arrays;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class CyclicRotationTest {

  Solution s = new Solution();

  @Test
  void test() {
    execute(new int[]{3, 8, 9, 7, 6}, 3);
  }

  void execute(int[] A, int K) {
    var params = "%s | %s".formatted(Arrays.toString(A), K);
    System.out.printf("%s :: %s%n",
        Arrays.toString(s.solution(A, K)),
        params
    );
  }

  class Solution {

    public int[] solution(int[] A, int K) {
      int[] output = new int[A.length];
      int outputI;
      for (int i = 0; i < A.length; i++) {
        outputI = (i + K) % A.length;
        output[outputI] = A[i];
      }
      return output;
    }
  }
}
