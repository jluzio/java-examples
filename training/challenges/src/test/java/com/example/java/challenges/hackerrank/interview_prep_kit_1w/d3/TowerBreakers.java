package com.example.java.challenges.hackerrank.interview_prep_kit_1w.d3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class TowerBreakers {

  record TestData(int n, int m) {

  }

  @Test
  void test() throws IOException {
    List.of(
        new TestData(2, 2),
        new TestData(1, 4)
    ).forEach(it -> System.out.printf("%s | %s%n", it, Result.towerBreakers(it.n(), it.m())));
  }


  class Result {

    /*
     * Complete the 'towerBreakers' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER m
     */

    public static int towerBreakers(int n, int m) {
      if (m == 1) return 2;
      return n % 2 == 1 ? 1 : 2;
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
          String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "")
              .split(" ");

          int n = Integer.parseInt(firstMultipleInput[0]);

          int m = Integer.parseInt(firstMultipleInput[1]);

          int result = Result.towerBreakers(n, m);

          bufferedWriter.write(String.valueOf(result));
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
