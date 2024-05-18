package com.example.java.challenges.codility.l8_leader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class DominatorTest {

  Solution s = new Solution();

  @Test
  void test() {
    execute(new int[]{3, 4, 3, 2, 3, -1, 3, 3});
    execute(new int[]{});
    execute(new int[]{0, 0, 1, 1});
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
      // leader occurs > N/2 elements
      // if 2 different elements are removed, the leader remains the same

      Map<Integer, List<Integer>> valueMap = new HashMap<>();

      for (int i = 0; i < A.length; i++) {
        int a = A[i];
        List<Integer> indexList = valueMap.computeIfAbsent(a, k -> new ArrayList<>());
        indexList.add(i);
      }

      Optional<Map.Entry<Integer, List<Integer>>> possibleLeader = valueMap.entrySet().stream()
          .max(Comparator.comparingInt(o -> o.getValue().size()));
      return possibleLeader
          .filter(v -> v.getValue().size() > (float) A.length / 2)
          .map(v -> v.getValue().get(0))
          .orElse(-1);
    }
  }

}
