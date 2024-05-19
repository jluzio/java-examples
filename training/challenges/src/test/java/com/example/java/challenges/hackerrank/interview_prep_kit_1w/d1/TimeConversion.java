package com.example.java.challenges.hackerrank.interview_prep_kit_1w.d1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;


class TimeConversion {

  @Test
  void test() {
    System.out.println(Result.timeConversion("07:05:45PM"));
  }

  class Result {

    /*
     * Complete the 'timeConversion' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING s as parameter.
     */

    public static String timeConversion(String s) {
      LocalTime localTime = LocalTime.parse(s, DateTimeFormatter.ofPattern("hh:mm:ssa"));
      return localTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

  }

  public class Solution {

    public static void main(String[] args) throws IOException {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
      BufferedWriter bufferedWriter = new BufferedWriter(
          new FileWriter(System.getenv("OUTPUT_PATH")));

      String s = bufferedReader.readLine();

      String result = Result.timeConversion(s);

      bufferedWriter.write(result);
      bufferedWriter.newLine();

      bufferedReader.close();
      bufferedWriter.close();
    }
  }

}
