package com.example.java.challenges.hackerrank.interview_prep_kit_1w.d2;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class DiagonalDifference {

  @Test
  void test() {
    System.out.println(Result.diagonalDifference(
        List.of(
            List.of(1, 2, 3),
            List.of(4, 5, 6),
            List.of(9, 8, 9)
        )));
  }

  class Result {

    /*
     * Complete the 'diagonalDifference' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts 2D_INTEGER_ARRAY arr as parameter.
     */

    public static int diagonalDifference(List<List<Integer>> arr) {
      int size = arr.get(0).size();
      int d1 = 0;
      int d2 = size - 1;
      int diff = 0;
      for (List<Integer> row : arr) {
        diff += row.get(d1++);
        diff -= row.get(d2--);
      }
      return Math.abs(diff);
    }
  }

  public class Solution {
    public static void main(String[] args) throws IOException {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

      int n = Integer.parseInt(bufferedReader.readLine().trim());

      List<List<Integer>> arr = new ArrayList<>();

      IntStream.range(0, n).forEach(i -> {
        try {
          arr.add(
              Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                  .map(Integer::parseInt)
                  .collect(toList())
          );
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      });

      int result = Result.diagonalDifference(arr);

      bufferedWriter.write(String.valueOf(result));
      bufferedWriter.newLine();

      bufferedReader.close();
      bufferedWriter.close();
    }
  }

}

