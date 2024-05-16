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
      // keep shifting to check the rightmost bit
      for (int v = N; v > 0; v >>= 1) {
        if ((v & 1) == 1) { // 1 was found, we are starting a sequence
          if (started) { // if we had already started, store the max value of the sequence
            max = curr > max ? curr : max;
          }
          started = true;
          curr = 0;
        } else { // zeroes
          curr++;
        }
      }
      return max;
    }
  }

}
