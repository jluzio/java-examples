package com.example.java.challenges.codility.l7_stacks_queues;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import org.junit.jupiter.api.Test;

class BracketsTest {

  Solution s = new Solution();

  @Test
  void test() {
    execute("{[()()]}"); // 1
    execute("([)()]"); // 0
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
      List<Character> startChars = Arrays.asList('{', '(', '[');
      List<Character> closeChars = Arrays.asList('}', ')', ']');

      Stack<Character> brackets = new Stack<>();
      int index;
      char startC;
      for (char c : S.toCharArray()) {
        index = startChars.indexOf(c);
        if (index >= 0) {
          brackets.push(c);
        } else {
          if (brackets.empty()) {
            return 0;
          }
          startC = brackets.pop();
          index = closeChars.indexOf(c);
          if (startChars.get(index) != startC) {
            return 0;
          }
        }
      }
      return brackets.empty() ? 1 : 0;
    }

  }

}
