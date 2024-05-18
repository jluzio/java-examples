package com.example.java.challenges.codility.l7_stacks_queues;

import org.junit.jupiter.api.Test;

class NestingTest {

  Solution s = new Solution();

  @Test
  void test() {
    execute("(()(())())");
    execute("())");
    execute(")()(");
  }

  @Test
  void testPerformance() {
    execute("(".repeat(500_000) + ")".repeat(500_000));
    execute("(".repeat(500_000) + ")".repeat(500_001));
  }

  void execute(String S) {
    var params = "%s".formatted(S);
    System.out.printf("%s :: %s%n",
        s.solution(S),
        params
    );
  }

  class Solution {

    public int solution(String S) {
      char open = '(';
      char close = ')';
      int openParenthesisCount = 0;
      char c;
      for (int i = 0; i < S.length(); i++) {
        c = S.charAt(i);
        if (c == open) {
          openParenthesisCount++;
        } else {
          openParenthesisCount--;
          if (openParenthesisCount < 0) {
            return 0;
          }
        }
      }
      return openParenthesisCount == 0 ? 1 : 0;
    }

  }

}
