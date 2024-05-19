package com.example.java.challenges.codility.l09_max_slices;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class MaxProfitTest {

  Solution s = new Solution();

  @Test
  void test() {
    execute(new int[]{23171, 21011, 21123, 21366, 21013, 21367});
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
      if (A.length == 0) {
        return 0;
      }

      int min = A[0];
      int profit;
      int maxProfit = 0;
      for (int i = 1; i < A.length; i++) {
        min = Math.min(A[i], min);
        profit = A[i] - min;
        maxProfit = Math.max(profit, maxProfit);
      }
      return maxProfit;
    }

  }

}
