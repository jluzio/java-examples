package com.example.java.challenges.hackerrank.interview_prep_kit_1w;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class Day3Test3CaesarCipher {

  record TestData(String s, int k) {

  }

  @Test
  void test() {
    Stream.of(
        new TestData("middle-Outz", 2),
        new TestData("Always-Look-on-the-Bright-Side-of-Life", 5)
    ).forEach(it -> System.out.printf("%s - %s%n", it, Result.caesarCipher(it.s(), it.k())));
  }

  class Result {

    /*
     * Complete the 'caesarCipher' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. STRING s
     *  2. INTEGER k
     */

    public static String caesarCipher(String s, int k) {
      // Write your code here
      return s.chars()
          .mapToObj(it -> (char) it)
          .map(c -> {
            int out;
            char anchor;
            if (Character.isLetter(c) && Character.isUpperCase(c)) {
              anchor = 'A';
              out = anchor + ((c - anchor + k) % 26);
            } else if (Character.isLetter(c) && Character.isLowerCase(c)) {
              anchor = 'a';
              out = anchor + ((c - anchor + k) % 26);
            } else {
              out = c;
            }
            char chOut = (char) out;
            return String.valueOf(chOut);
          })
          .collect(Collectors.joining(""));
    }

  }

  public class Solution {

    public static void main(String[] args) throws IOException {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
      BufferedWriter bufferedWriter = new BufferedWriter(
          new FileWriter(System.getenv("OUTPUT_PATH")));

      int n = Integer.parseInt(bufferedReader.readLine().trim());

      String s = bufferedReader.readLine();

      int k = Integer.parseInt(bufferedReader.readLine().trim());

      String result = Result.caesarCipher(s, k);

      bufferedWriter.write(result);
      bufferedWriter.newLine();

      bufferedReader.close();
      bufferedWriter.close();
    }
  }

}
