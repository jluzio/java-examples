package com.example.java.challenges.codility.l2_arrays;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OddOccurrencesInArrayTest {

  Solution s = new Solution();

  @Test
  void test() {
    execute(new int[]{9, 3, 9, 3, 9, 7, 9});
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
      Map<Integer, Integer> count = new HashMap<Integer, Integer>();
      for (int v : A) {
        count.put(
            v,
            count.getOrDefault(v, 0) + 1);
      }
      return count.entrySet().stream()
          .filter(e -> e.getValue() % 2 == 1)
          .map(Map.Entry::getKey)
          .findFirst()
          .get();
    }
  }
}
