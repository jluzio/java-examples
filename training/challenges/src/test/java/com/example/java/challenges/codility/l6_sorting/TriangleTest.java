package com.example.java.challenges.codility.l6_sorting;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class TriangleTest {

  Solution s = new Solution();

  @Test
  void test() {
    execute(new int[]{10, 2, 5, 1, 8, 20});
    execute(new int[]{10, 50, 5, 1});
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
      // this problem doesn't state that you can reorder the array,
      // but it's the only way for the solution given the performance restrictions
      // i guess it was supposed to be implied since this is a sorting lesson,
      // but it's only clear after trying that solution and dry-running some cases.
      // Note that 3 elements close to each other have the best chance (unclear how to prove) to fulfill the triangle rules
      Arrays.sort(A);
      for (int i = 0; i < A.length - 2; i++) {
        if (isTriangular(A, i, i + 1, i + 2)) {
          return 1;
        }
      }
      return 0;
    }

    public boolean isTriangular(int[] A, int P, int Q, int R) {
      long aP = A[P];
      long aQ = A[Q];
      long aR = A[R];
      return aP + aQ > aR
          // not needed if sorted
          && aQ + aR > aP
          // not needed if sorted
          && aR + aP > aQ;
    }
  }

}
