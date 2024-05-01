package com.example.java.challenges.hackerrank.interview_prep_kit_1w;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class Day4Test1GridChallenge {

  record TestData(List<String> grid) {

  }

  @Test
  void test() {
    Stream.of(
        new TestData(List.of("ebacd", "fghij", "olmkn", "trpqs", "xywuv")),
        new TestData(List.of("abc", "lmp", "qrt")),
        new TestData(List.of("mpxz", "abcd", "wlmf")),
        new TestData(List.of("abc", "hjk", "mpq", "rtv"))
    ).forEach(it ->
        System.out.printf("%s | %s%n", it, Result.gridChallenge(it.grid()))
    );
  }

  class Result {

    /*
     * Complete the 'gridChallenge' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING_ARRAY grid as parameter.
     */

    public static String gridChallenge(List<String> grid) {
      // Write your code here
      List<List<Integer>> sortedGridChars = grid.stream()
          .map(it -> it.chars().sorted().boxed().collect(Collectors.toList()))
          .collect(Collectors.toList());

      int rowN = grid.size();
      int cellN = grid.get(0).length();

      // cols
      for (int col = 0; col < cellN; col++) {
        Integer cell = sortedGridChars.get(0).get(col);
        for (int row = 1; row < rowN; row++) {
          Integer currCell = sortedGridChars.get(row).get(col);
          if (cell.compareTo(currCell) > 0) {
            return "NO";
          }
          cell = currCell;
        }
      }
      return "YES";
    }
  }

  public class Solution {

    public static void main(String[] args) throws IOException {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
      BufferedWriter bufferedWriter = new BufferedWriter(
          new FileWriter(System.getenv("OUTPUT_PATH")));

      int t = Integer.parseInt(bufferedReader.readLine().trim());

      IntStream.range(0, t).forEach(tItr -> {
        try {
          int n = Integer.parseInt(bufferedReader.readLine().trim());

          List<String> grid = IntStream.range(0, n).mapToObj(i -> {
                try {
                  return bufferedReader.readLine();
                } catch (IOException ex) {
                  throw new RuntimeException(ex);
                }
              })
              .collect(toList());

          String result = Result.gridChallenge(grid);

          bufferedWriter.write(result);
          bufferedWriter.newLine();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      });

      bufferedReader.close();
      bufferedWriter.close();
    }
  }

}
