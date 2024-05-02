package com.example.java.challenges.hackerrank.interview_prep_2024;

import org.junit.jupiter.api.Test;

class PrintMultiplicationTable {

  @Test
  void test() {
    Solution.printMultiplicationTable(12, 12);
  }

  /*
   * Can you code a function which takes two arguments, x and y,
   * and prints out a multiplication table based on the values
   * passed in.
   *
   * Example invocation:
   *
   * printMultiplicationTable(12, 5);
   *
   * Example output:
   *
   *   1   2   3   4   5   6   7   8   9  10  11  12
   *   2   4   6   8  10  12  14  16  18  20  22  24
   *   3   6   9  12  15  18  21  24  27  30  33  36
   *   4   8  12  16  20  24  28  32  36  40  44  48
   *   5  10  15  20  25  30  35  40  45  50  55  60
   *
   */
  class Solution {

    public static void main(String[] args) {
      printMultiplicationTable(3, 3);
    }

    public static void printMultiplicationTable(int x, int y) {
      int maxVal = x * y;
      int maxLen = String.valueOf(maxVal).length();
      for (int currY = 1; currY <= y; currY++) {
        for (int currX = 1; currX <= x; currX++) {
          int val = currX * currY;
          System.out.printf(" %" + maxLen + "s", val);
        }
        System.out.println();
      }

    }
  }

}
