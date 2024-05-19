package com.example.java.challenges.hackerrank.interview_prep_kit_1w.d4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class RecursiveDigitSum {

  record Data(String n, int k){}

  @Test
  void test() {
    Stream.of(
        new Data("9875", 4)
    ).forEach(it ->
        System.out.printf("%s | %s%n", it, Result.superDigit(it.n(), it.k())));
  }

  class Result {

    /*
     * Complete the 'superDigit' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. STRING n
     *  2. INTEGER k
     */

    public static int superDigit(String n, int k) {
      if (k > 1) {
        String value = String.valueOf(superDigit(n, 1));
        String currentValue = value;
        for (int i=1; i<k; i++) {
          currentValue = String.valueOf(superDigit(currentValue + value, 1));
        }
        return Integer.parseInt(currentValue);
      } else {
        int value = n.chars()
            .map(c -> Integer.parseInt(String.valueOf((char)c)))
            .sum();
        if (value < 10) {
          return value;
        } else {
          return superDigit(String.valueOf(value), k);
        }
      }
    }

  }

  public class Solution {

    public static void main(String[] args) throws IOException {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
      BufferedWriter bufferedWriter = new BufferedWriter(
          new FileWriter(System.getenv("OUTPUT_PATH")));

      String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

      String n = firstMultipleInput[0];

      int k = Integer.parseInt(firstMultipleInput[1]);

      int result = Result.superDigit(n, k);

      bufferedWriter.write(String.valueOf(result));
      bufferedWriter.newLine();

      bufferedReader.close();
      bufferedWriter.close();
    }
  }

}
