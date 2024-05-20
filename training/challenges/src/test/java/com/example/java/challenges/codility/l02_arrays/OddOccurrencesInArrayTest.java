package com.example.java.challenges.codility.l02_arrays;

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
//      return solutionUsingXor(A);
      return solutionUsingCount(A);
    }

    public int solutionUsingCount(int[] A) {
      // store counts
      Map<Integer, Integer> countOfNumberMap = new HashMap<Integer, Integer>();
      for (int v : A) {
        countOfNumberMap.put(
            v,
            countOfNumberMap.getOrDefault(v, 0) + 1);
      }
      // search for the count that is odd
      return countOfNumberMap.entrySet().stream()
          .filter(e -> e.getValue() % 2 == 1)
          .map(Map.Entry::getKey)
          .findFirst()
          .get();
    }

    public int solutionUsingXor(int[] A) {
      // a xor 0 = a | a xor a == 0 | a xor b = ?
      int xorAccumulator = 0;
      for (int a : A) {
        xorAccumulator ^= a;
      }
      return xorAccumulator;
    }
  }
}
