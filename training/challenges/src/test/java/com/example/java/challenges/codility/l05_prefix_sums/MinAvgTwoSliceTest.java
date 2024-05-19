package com.example.java.challenges.codility.l05_prefix_sums;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class MinAvgTwoSliceTest {

  Solution s = new Solution(true);

  @Test
  void test() {
    execute(new int[]{4, 2, 2, 5, 1, 5, 8});
  }

  void execute(int[] A) {
    var params = "%s".formatted(Arrays.toString(A));
    System.out.printf("%s :: %s%n",
        s.solution(A),
        params
    );
  }

  class Solution {

    public boolean debug = false;

    public Solution() {
    }

    public Solution(boolean debug) {
      this.debug = debug;
    }

    public int solution(int[] A) {
      double minAvg = (A[1] + A[0]) / 2;
      int minAvgStart = 0;
      int minAvgEnd = 1;

      double avg;
      for (int i = 0; i < A.length; i++) {
        int sum = A[i];
        int nElements = 1;
        for (int j = i + 1; j < A.length; j++) {
          sum += A[j];
          nElements++;
          // try to reduce the iterations to check by limiting to 3 elements,
          // or more if the added value is lower than the average so far
          if (nElements <= 3 || A[j] < minAvg) {
            avg = sum / (double) nElements;
            if (avg < minAvg) {
              minAvg = avg;
              minAvgStart = i;
              minAvgEnd = j;
            }
          } else {
            break;
          }
        }
      }
      if (debug) {
        System.out.printf("avg: %s - %s%n", minAvgStart, minAvgEnd);
      }
      return minAvgStart;
    }

  }

}
