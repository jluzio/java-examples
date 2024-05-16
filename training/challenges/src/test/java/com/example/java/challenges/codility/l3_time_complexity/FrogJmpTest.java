package com.example.java.challenges.codility.l3_time_complexity;

import org.junit.jupiter.api.Test;

class FrogJmpTest {

  Solution s = new Solution();

  @Test
  void test() {
    execute(10, 85, 30);
  }

  void execute(int X, int Y, int D) {
    var params = "%s | %s | %s".formatted(X, Y, D);
    System.out.printf("%s :: %s%n",
        s.solution(X, Y, D),
        params
    );
  }

  class Solution {

    public int solution(int X, int Y, int D) {
      // calculate number of jumps using formula
      return (int) Math.ceil(((double) (Y - X)) / D);
    }
  }
}
