package com.example.java.challenges.hackerrank.interview_prep_kit_1w.d2;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;


class MockTest {

  @Test
  void test() {
    System.out.println(Result.flippingMatrix(
        List.of(
            List.of(112, 42, 83, 119),
            List.of(56, 125, 56, 49),
            List.of(15, 78, 101, 43),
            List.of(62, 98, 114, 108)
        )));
  }

  class Result {

    /*
     * Complete the 'timeConversion' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING s as parameter.
     */

    public static int flippingMatrix(List<List<Integer>> matrix) {
      // a b b a
      // c d d c
      // c d d c
      // a b b a

      int n = matrix.size() / 2;
      int size = matrix.size();

      int total = 0;
      for (int row = 0; row < n; row++) {
        for (int col = 0; col < n; col++) {
          int max = Stream.of(
                  matrix.get(row).get(col),
                  matrix.get(row).get(size - col - 1),
                  matrix.get(size - row - 1).get(col),
                  matrix.get(size - row - 1).get(size - col - 1)
              )
              .max(Integer::compareTo)
              .get();
          total += max;
        }
      }
      return total;
    }

  }

}
