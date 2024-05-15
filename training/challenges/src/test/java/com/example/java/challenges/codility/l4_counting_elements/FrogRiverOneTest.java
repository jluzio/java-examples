package com.example.java.challenges.codility.l4_counting_elements;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class FrogRiverOneTest {

  Solution s = new Solution();

  @Test
  void test() {
    execute(5, new int[]{1, 3, 1, 4, 2, 3, 5, 4});
    execute(5, new int[]{1, 3, 1, 4, 2, 3, 5, 4});
  }

  void execute(int X, int[] A) {
    var params = "%s | %s".formatted(X, Arrays.toString(A));
    System.out.printf("%s :: %s%n",
        s.solution(X, A),
        params
    );
  }

  class Solution {

    public int solution(int X, int[] A) {
      // Implement your solution here
      Map<Integer, Integer> timesPerPosition = new HashMap<Integer, Integer>();
      Integer minTimeInPosition;
      for (int time = 0, pos; time < A.length; time++) {
        pos = A[time];
        minTimeInPosition = timesPerPosition.get(pos);
        if (minTimeInPosition == null || minTimeInPosition > time) {
          timesPerPosition.put(pos, time);
        }
      }

      int crossTime = -1;
      for (int pos = 1; pos <= X; pos++) {
        minTimeInPosition = timesPerPosition.get(pos);
        if (minTimeInPosition == null) {
          crossTime = -1;
          break;
        }
        crossTime = Math.max(minTimeInPosition, crossTime);
      }
      return crossTime;
    }
  }
}
