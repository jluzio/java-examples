package com.example.java.challenges.codility.l8_prime_and_composite_numbers;

import org.junit.jupiter.api.Test;

class MinPerimeterRectangleTest {

  Solution s = new Solution();

  @Test
  void test() {
//    execute(30); // 22
    execute(36); // 24
//    execute(1); // 4
  }

  void execute(int N) {
    var params = "%s".formatted(N);
    System.out.printf("%s :: %s%n",
        s.solution(N),
        params
    );
  }

  class Solution {

    public int solution(int N) {
      int i = 1;
      double sqrtN = Math.sqrt(N);
      int minPerimeter = Integer.MAX_VALUE, perimeter;
      for (; i < sqrtN; i++) {
        if (N % i == 0) {
          perimeter = (i + N/i) * 2;
          minPerimeter = Math.min(perimeter, minPerimeter);
        }
      }
      if (i == sqrtN) {
        perimeter = (i + N/i) * 2;
        minPerimeter = Math.min(perimeter, minPerimeter);
      }
      return minPerimeter;
    }

  }

}
