package com.example.java.challenges.hackerrank.interview_prep_kit_1w.d2;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class CountingSort1 {

  @Test
  void test() {
    System.out.println(Result.countingSort(
        List.of(1, 1, 3, 2, 1)
    ));
  }

  class Result {

    /*
     * Complete the 'countingSort' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts INTEGER_ARRAY arr as parameter.
     */

    public static List<Integer> countingSort(List<Integer> arr) {
      int[] output = new int[100];
      for (Integer integer : arr) {
        output[integer]++;
      }
      return Arrays.stream(output)
          .boxed()
          .collect(Collectors.toList());
    }

  }

  public class Solution {

    public static void main(String[] args) throws IOException {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
      BufferedWriter bufferedWriter = new BufferedWriter(
          new FileWriter(System.getenv("OUTPUT_PATH")));

      int n = Integer.parseInt(bufferedReader.readLine().trim());

      List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
          .map(Integer::parseInt)
          .collect(toList());

      List<Integer> result = Result.countingSort(arr);

      bufferedWriter.write(
          result.stream()
              .map(Object::toString)
              .collect(joining(" "))
              + "\n"
      );

      bufferedReader.close();
      bufferedWriter.close();
    }
  }

}
