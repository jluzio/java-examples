package com.example.java.challenges.codility.l3_time_complexity;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class PermMissingElemTest {

  Solution s = new Solution();

  record Data(int[] A) {

  }

  @Test
  void test() {
    execute(new int[]{2, 3, 1, 5});
    execute(new int[]{2});
    execute(new int[]{});
    execute(new int[]{1});
    execute(new int[]{1, 3, 2});
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
      if (A.length == 0) { // 0 elements -> 1
        return 1;
      }

      // sort and then check expected
      java.util.Arrays.sort(A);
      int expected = 1;
      for (; expected <= A.length; expected++) {
        if (A[expected - 1] != expected) { // expected is missing
          return expected;
        }
      }
      // if all elements were ok, expected is next
      return expected;
    }
  }
}
