package com.example.java.challenges.codility.l10_prime_and_composite_numbers;

import org.junit.jupiter.api.Test;

class CountFactorsTest {

  Solution s = new Solution();

  @Test
  void test() {
    execute(24);
  }

  void execute(int N) {
    var params = "%s".formatted(N);
    System.out.printf("%s :: %s%n",
        s.solution(N),
        params
    );
  }

  class Solution {

    public int solution(int N) {
      int factors = 0;
      int i = 1;
      double sqrtN = Math.sqrt(N);
      for (; i < sqrtN; i++) {
        if (N % i == 0) {
          factors += 2;
        }
      }
      if (i * i == N) {
        factors++;
      }
      return factors;
    }

  }

}
