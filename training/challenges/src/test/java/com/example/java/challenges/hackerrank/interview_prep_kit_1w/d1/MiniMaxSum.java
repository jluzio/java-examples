package com.example.java.challenges.hackerrank.interview_prep_kit_1w.d1;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class MiniMaxSum {

  @Test
  void test() {
    Result.miniMaxSum(List.of(1, 5, 3, 7, 9));
  }

  class Result {

    /*
     * Complete the 'miniMaxSum' function below.
     *
     * The function accepts INTEGER_ARRAY arr as parameter.
     */

    public static void miniMaxSum(List<Integer> arr) {
      List<Integer> sorted = arr.stream().sorted().collect(toList());
      long min = 0, max = 0;
      for (int i = 0; i < 4; i++) {
        min += sorted.get(i);
      }
      for (int i = sorted.size() - 4; i < sorted.size(); i++) {
        max += sorted.get(i);
      }
      System.out.printf("%d %d%n", min, max);
    }

  }

  public class Solution {

    public static void main(String[] args) throws IOException {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

      List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
          .map(Integer::parseInt)
          .collect(toList());

      Result.miniMaxSum(arr);

      bufferedReader.close();
    }
  }

}
