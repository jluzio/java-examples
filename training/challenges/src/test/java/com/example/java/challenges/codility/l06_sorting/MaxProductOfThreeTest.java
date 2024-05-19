package com.example.java.challenges.codility.l06_sorting;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class MaxProductOfThreeTest {

  Solution s = new Solution();

  @Test
  void test() {
    execute(new int[]{-3, 1, 2, -2, 5, 6});
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
      Arrays.sort(A);
      int n = A.length;
      int maxTripletProduct = A[n - 3] * A[n - 2] * A[n - 1];
      if (A[0] < 0 && A[1] < 0) {
        int tripletProductHighestNegativeNumbers = A[0] * A[1] * A[n - 1];
        maxTripletProduct = Math.max(maxTripletProduct, tripletProductHighestNegativeNumbers);
      }
      return maxTripletProduct;
    }
  }

}
