package com.example.java.challenges.codility.l7_stacks_queues;

import java.util.Arrays;
import java.util.Stack;
import org.junit.jupiter.api.Test;

class FishTest {

  Solution s = new Solution();

  @Test
  void test() {
    execute(new int[]{4, 3, 2, 1, 5}, new int[]{0, 1, 0, 0, 0});
    execute(new int[]{}, new int[]{});
    execute(new int[]{4, 2, 3, 1, 5}, new int[]{0, 1, 0, 0, 0});
  }

  void execute(int[] A, int[] B) {
    var params = "%s | %s".formatted(Arrays.toString(A), Arrays.toString(B));
    System.out.printf("%s :: %s%n",
        s.solution(A, B),
        params
    );
  }

  class Solution {

    public int solution(int[] A, int[] B) {
      Stack<Integer> downstream = new Stack<>();
      Stack<Integer> upstream = new Stack<>();
      boolean survivedCrossing;

      for (int i = 0; i < A.length; i++) {
        int fishSize = fishSize(A, i);
        if (isUpstream(B, i)) {
          survivedCrossing = fishCrossing(fishSize, downstream);
          if (survivedCrossing) {
            upstream.push(fishSize);
          }
        } else {
          downstream.push(fishSize);
        }
      }
      return downstream.size() + upstream.size();
    }

    boolean fishCrossing(int fishSize, Stack<Integer> otherDirectionFishes) {
      while (!otherDirectionFishes.empty()) {
        int otherFishSize = otherDirectionFishes.peek();
        if (fishSize > otherFishSize) { // eat other fish
          otherDirectionFishes.pop();
        } else {
          // got eaten
          return false;
        }
      }
      // survived
      return true;
    }

    int fishSize(int[] A, int i) {
      return A[i];
    }
    boolean isUpstream(int[] B, int i) {
      return B[i] == 0;
    }

  }

}
