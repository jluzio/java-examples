package com.example.java.challenges.hackerrank.interview_prep_2024;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class MultiplyMatrixWithMod {

  @Test
  void test() {
    Solution.multiplyMatrix(
        List.of(
            List.of(1, 2, 3),
            List.of(4, 5, 6)
        ),
        List.of(
            List.of(10, 11),
            List.of(20, 21),
            List.of(30, 31)
        )
    );
  }

  class Solution {

    /*
      [1 2 3] x [10 11] = [1*10+2*20+3*30  1*11+2*21+3*31] (dim(y,x) = aY,bX)
      [4 5 6]   [20 21]   [4*10+5*20+6*30  4*11+5*21+6*31]
                [30 31]
     */

    public static void multiplyMatrix(List<List<Integer>> a, List<List<Integer>> b) {
      var resultMatrix = new int[a.size()][b.get(0).size()];
      for (int y = 0; y < resultMatrix.length; y++) {
        for (int x = 0; x < resultMatrix[y].length; x++) {
          resultMatrix[y][x] = computeCellValue(a, b, y, x);
        }
      }
      List<List<Integer>> resultMatrixList = new ArrayList<>();
      for (int[] row : resultMatrix) {
        List<Integer> resultRow = new ArrayList<>();
        for (int cell : row) {
          resultRow.add(cell);
        }
        resultMatrixList.add(resultRow);
      }
      System.out.println(resultMatrixList);
    }

    public static int computeCellValue(List<List<Integer>> a, List<List<Integer>> b, int y, int x) {
      int total = 0;
      for (int n = 0; n < b.size(); n++) {
        total += a.get(y).get(n) * b.get(n).get(x);
      }
      return total;
    }

    public static void multiplyMatrixWithMod(List<List<Integer>> a, List<List<Integer>> b) {

    }
  }

}
