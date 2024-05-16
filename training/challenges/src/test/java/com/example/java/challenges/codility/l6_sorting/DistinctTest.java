package com.example.java.challenges.codility.l6_sorting;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DistinctTest {

  Solution s = new Solution();

  @Test
  void test() {
    execute(new int[]{2, 1, 1, 2, 3, 1});
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
      Set<Integer> uniqueValues = new HashSet<>();
      Arrays.stream(A).forEach(uniqueValues::add);
      return uniqueValues.size();
    }
  }

}
