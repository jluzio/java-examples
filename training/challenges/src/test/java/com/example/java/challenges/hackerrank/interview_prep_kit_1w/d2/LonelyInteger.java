package com.example.java.challenges.hackerrank.interview_prep_kit_1w.d2;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.*;
import org.junit.jupiter.api.Test;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class LonelyInteger {

  @Test
  void test() {
    System.out.println(Result.lonelyinteger(List.of(1, 1, 3, 2, 2, 4, 3)));
  }

  class Result {

    /*
     * Complete the 'lonelyinteger' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts INTEGER_ARRAY a as parameter.
     */

    public static int lonelyinteger(List<Integer> a) {
      Map<Integer, List<Integer>> collect = a.stream()
          .collect(Collectors.groupingBy(v -> v));
      return collect.entrySet().stream()
          .filter(e -> e.getValue().size() == 1)
          .map(Entry::getKey)
          .findFirst()
          .orElseThrow();
    }

  }

  public class Solution {
    public static void main(String[] args) throws IOException {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

      int n = Integer.parseInt(bufferedReader.readLine().trim());

      List<Integer> a = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
          .map(Integer::parseInt)
          .collect(toList());

      int result = Result.lonelyinteger(a);

      bufferedWriter.write(String.valueOf(result));
      bufferedWriter.newLine();

      bufferedReader.close();
      bufferedWriter.close();
    }
  }

}

