package com.example.java.challenges.codility.l03_time_complexity;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class TapeEquilibriumTest {

  Solution s = new Solution();

  @Test
  void test() {
    execute(new int[]{3, 1, 2, 4, 3});
    execute(new int[]{1000, -1000});
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
      // NOTE: could also use int since: 100_000 * 1000 < Integer.MAX_VALUE
      long sum = 0;
      for (int v : A) {
        sum += v;
      }
      long pLeft = 0;
      long pRight = sum;
      long diff = Long.MAX_VALUE;
      long currDiff;

      // check partition sums by deriving from total sum
      // then check both values
      for (int p = 1; p < A.length; p++) {
        pLeft += A[p - 1];
        pRight -= A[p - 1];
        currDiff = Math.abs(pLeft - pRight);
        if (currDiff < diff) {
          diff = currDiff;
        }
      }
      return (int) diff;
    }
  }
}
