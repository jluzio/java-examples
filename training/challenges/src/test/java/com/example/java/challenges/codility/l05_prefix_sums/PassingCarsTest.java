package com.example.java.challenges.codility.l05_prefix_sums;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class PassingCarsTest {

  Solution s = new Solution();

  @Test
  void test() {
    execute(new int[]{0, 1, 0, 1, 1});
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
      // Implement your solution here
      int passingLeftCount = 0;
      int passingRightCount = 0;
      int count = 0;
      for (int i = 0, carDir; i < A.length; i++) {
        carDir = A[i];
        // using the sum of cars seen to add to each iteration, if car is going right
        // it becomes clearer dry running it
        if (carDir == 1) {
          passingRightCount++;
          count += passingLeftCount;
          if (count > 1_000_000_000) {
            return -1;
          }
        } else {
          passingLeftCount++;
        }
      }
      return count;
    }
  }
}
