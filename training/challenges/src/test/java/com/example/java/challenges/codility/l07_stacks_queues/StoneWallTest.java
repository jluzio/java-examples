package com.example.java.challenges.codility.l07_stacks_queues;

import java.util.Arrays;
import java.util.Stack;
import org.junit.jupiter.api.Test;

class StoneWallTest {

  Solution s = new Solution();

  @Test
  void test() {
    execute(new int[]{8, 8, 5, 7, 9, 8, 7, 4, 8});
  }

  void execute(int[] H) {
    var params = "%s".formatted(Arrays.toString(H));
    System.out.printf("%s :: %s%n",
        s.solution(H),
        params
    );
  }

  class Solution {

    public int solution(int[] H) {
      Stack<Integer> bricks = new Stack<>();
      int brickCount = 0;
      for (int i = 0; i < H.length; i++) {
        int height = H[i];
        if (bricks.empty()) {
          bricks.push(height);
          brickCount++;
        }
        else {
          int currBrick = bricks.peek();
          if (height > currBrick) {
            bricks.push(height);
            brickCount++;
          } else if (height < currBrick) {
            while (!bricks.empty() && height < bricks.peek()) {
              bricks.pop();
            }
            if (bricks.empty() || bricks.peek() != height) {
              bricks.push(height);
              brickCount++;
            }
          }
        }
      }
      return brickCount;
    }

  }

}
