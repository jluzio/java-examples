package com.example.java.challenges.codility.l1_iterations;

import org.junit.jupiter.api.Test;

class BinaryGapTest {

  @Test
  void test() {
    var solution = new Solution();
    System.out.println(solution.solution(9));
//    System.out.println(solution.solution(32));
  }

  class Solution {

    public int solution(int N) {
      int max = 0;
      int curr = 0;
      boolean started = false;
      for (int v = N; v > 0; v >>= 1) {
        if ((v & 1) == 1) {
          if (started) {
            max = curr > max ? curr : max;
          }
          started = true;
          curr = 0;
        } else {
          curr++;
        }
      }
      return max;
    }
  }

}
