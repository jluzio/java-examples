package com.example.java.challenges.hackerrank.interview_prep_kit_1w;

import java.io.*;
import java.util.*;
import java.util.stream.*;
import org.junit.jupiter.api.Test;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Day1Test1PlusMinus {

  @Test
  void test() throws IOException {

    Result.plusMinus(List.of(1, 1, 0, -1, -1));

  }

  class Result {

    /*
     * Complete the 'plusMinus' function below.
     *
     * The function accepts INTEGER_ARRAY arr as parameter.
     */

    public static void plusMinus(List<Integer> arr) {
      // Write your code here
      int positives = 0;
      int negatives = 0;
      int zeroes = 0;
      int count = arr.size();
      for (Integer integer : arr) {
        if (integer == 0) {
          zeroes++;
        } else if (integer > 0) {
          positives++;
        } else {
          negatives++;
        }
      }
      printValue(positives, count);
      printValue(negatives, count);
      printValue(zeroes, count);
    }

    public static void printValue(int results, int count) {
      double value = (double) results / count;
      System.out.printf("%.6f%n", value);
    }

  }

  public class Solution {
    public static void main(String[] args) throws IOException {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

      int n = Integer.parseInt(bufferedReader.readLine().trim());

      List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
          .map(Integer::parseInt)
          .collect(toList());

      Result.plusMinus(arr);

      bufferedReader.close();
    }
  }

}
