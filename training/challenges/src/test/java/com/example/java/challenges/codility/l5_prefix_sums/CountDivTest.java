package com.example.java.challenges.codility.l5_prefix_sums;

import org.junit.jupiter.api.Test;

class CountDivTest {

  Solution s = new Solution();

  @Test
  void test() {
    execute(6, 11, 2);
    execute(5, 15, 3);
    execute(5, 15, 4);
    execute(0, 0, 4);
    execute(0, 2_000_000_000, 2);
    execute(1, 2_000_000_000, 2);
    execute(1, 2_000_000_000, 1_000_000_000);
  }

  void execute(int A, int B, int K) {
    var params = "%s | %s | %s".formatted(A, B, K);
    System.out.printf("%s :: %s%n",
        s.solution(A, B, K),
        params
    );
  }

  class Solution {

    public int solution(int A, int B, int K) {
      // number of divisions, gotten by the int division, which translates to number of divisors
      int oneToMaxCount = B / K;
      int oneToMinCount = A / K;
      boolean includeMinInCount = A % K == 0;
      // divisors from a sub range is the difference from
      // the divisors from 0 to start, the divisors from 0 to end
      // and checking if start is divisor
      return oneToMaxCount - oneToMinCount + (includeMinInCount ? 1 : 0);
    }
  }
}
