package com.example.java.challenges.codility.l08_leader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class EquiLeaderTest {

  Solution s = new Solution();

  @Test
  void test() {
//    execute(new int[]{4, 3, 4, 4, 4, 2}); // 2
//    execute(new int[]{4, 4, 2, 5, 3, 4, 4, 4}); // 3
    execute(new int[]{1, 2, 1, 1, 2, 1}); // 3
    execute(new int[]{}); // 0
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

      Optional<Map.Entry<Integer, List<Integer>>> maybePossibleLeader = valueMap.entrySet().stream()
          .max(Comparator.comparingInt(o -> o.getValue().size()));
      if (!maybePossibleLeader.isPresent()
          || maybePossibleLeader.map(v -> v.getValue().size() <= A.length / 2).orElse(false)) {
        return 0;
      }
      int leader = maybePossibleLeader.get().getKey();
      List<Integer> leaderIndexes = maybePossibleLeader.get().getValue();
      int equiLeaderCount = 0;
      int currLeaderIdx = 0;
      for (int i = leaderIndexes.get(0); i < leaderIndexes.get(leaderIndexes.size() - 1); i++) {
        while (currLeaderIdx + 1 < leaderIndexes.size() && leaderIndexes.get(currLeaderIdx + 1) <= i) {
          currLeaderIdx++;
        }
        int occursLeft = currLeaderIdx + 1;
        int occursRight = leaderIndexes.size() - occursLeft;
        int totalElementsLeft = i + 1;
        int totalElementsRight = A.length - totalElementsLeft;

        boolean leftSeqHasLeader = occursLeft > (float) totalElementsLeft / 2;
        boolean rightSeqHasLeader = occursRight > 0 && occursRight > (float) totalElementsRight / 2;

        if (leftSeqHasLeader && rightSeqHasLeader) {
          equiLeaderCount++;
        }
      }

      return equiLeaderCount;
    }
  }

}
