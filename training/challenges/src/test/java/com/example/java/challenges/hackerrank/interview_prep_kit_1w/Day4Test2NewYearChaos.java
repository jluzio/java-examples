package com.example.java.challenges.hackerrank.interview_prep_kit_1w;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class Day4Test2NewYearChaos {

  record Data(List<Integer> q, String expected) {

  }

  @Test
  void test() {
    Stream.of(
//        new Data(List.of(1, 2, 3, 5, 4, 6, 7, 8), "1"),
//        new Data(List.of(4, 1, 2, 3), "Too chaotic"),
//        new Data(List.of(2, 1, 5, 3, 4), "3"),
//        new Data(List.of(2, 5, 1, 3, 4), "Too chaotic"),
        new Data(List.of(1, 2, 5, 3, 7, 8, 6, 4), "7")
    ).forEach(it -> {
      System.out.printf("%s%n", it);
      Result.minimumBribes(it.q());
    });
  }

  class Result {

    /*
     * Complete the 'minimumBribes' function below.
     *
     * The function accepts INTEGER_ARRAY q as parameter.
     */

    public static void minimumBribes(List<Integer> q) {
      // Write your code here
      int bribes = 0;
      for (int i = 0; i < q.size(); i++) {
        int val = q.get(i);

        int originalIndex = val - 1;
        if (originalIndex - i > 2) {
          System.out.println("Too chaotic");
          return;
        }

        for (int j = Math.max(0, originalIndex - 1); j < i; j++) {
          if (q.get(j) > val) {
            bribes++;
          }
        }
      }
      System.out.println(bribes);
    }

  }

  public class Solution {

    public static void main(String[] args) throws IOException {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

      int t = Integer.parseInt(bufferedReader.readLine().trim());

      IntStream.range(0, t).forEach(tItr -> {
        try {
          int n = Integer.parseInt(bufferedReader.readLine().trim());

          List<Integer> q = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
              .map(Integer::parseInt)
              .collect(toList());

          Result.minimumBribes(q);
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
      });

      bufferedReader.close();
    }
  }

}
