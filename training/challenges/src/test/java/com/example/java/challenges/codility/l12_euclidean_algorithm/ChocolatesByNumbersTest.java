package com.example.java.challenges.codility.l12_euclidean_algorithm;

import org.junit.jupiter.api.Test;

class ChocolatesByNumbersTest {

  Solution s = new Solution();

  @Test
  void test() {
    execute(10, 4);
    execute(8, 4);
    execute(12, 4);
  }

  void execute(int N, int M) {
    var params = "%s | %s".formatted(N, M);
    System.out.printf("%s :: %s%n",
        s.solution(N, M),
        params
    );
  }

  class Solution {

    public int solution(int N, int M) {
      // N*a = M*b
      // b = N*a/M = N*a/gdc*c
      // b = N*a/M = gdc * z
      int gdc = gcd(N, M, 1);
      return N / gdc;
    }

    public int gcd(int a, int b, int res) {
      if (a == b) {
        return res * a;
      } else if (a % 2 == 0 && b % 2 == 0) {
        return gcd(a / 2, b / 2, res * 2);
      } else if (a % 2 == 0) {
        return gcd(a / 2, b, res);
      } else if (b % 2 == 0) {
        return gcd(a, b / 2, res);
      } else if (a > b) {
        return gcd(b, a - b, res);
      } else {
        return gcd(b - a, a, res);
      }
    }
  }

}
