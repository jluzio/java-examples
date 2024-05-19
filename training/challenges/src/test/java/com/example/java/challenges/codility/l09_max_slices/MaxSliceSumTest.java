package com.example.java.challenges.codility.l09_max_slices;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class MaxSliceSumTest {

  Solution s = new Solution();

  @Test
  void test() {
    execute(new int[]{3, 2, -6, 4, 0});
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
      int max_slice = A[0];
      int max_ending = A[0];
      for (int i = 1; i < A.length; i++) {
        max_ending = Math.max(A[i], max_ending + A[i]);
        max_slice = Math.max(max_ending, max_slice);
      }
      return max_slice;
    }

  }

}
