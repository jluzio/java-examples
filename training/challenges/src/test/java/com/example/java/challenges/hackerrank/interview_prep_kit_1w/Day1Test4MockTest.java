package com.example.java.challenges.hackerrank.interview_prep_kit_1w;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.Test;


class Day1Test4MockTest {

  @Test
  void test() {
    System.out.println(Result.findMedian(List.of(0, 1, 3, 2, 4, 5, 6)));
  }

  class Result {

    /*
     * Complete the 'timeConversion' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING s as parameter.
     */

    public static int findMedian(List<Integer> arr) {
      // Write your code here
      return arr.stream()
          .sorted()
          .skip(arr.size() / 2)
          .findFirst()
          .orElseThrow();
    }

 }

}
