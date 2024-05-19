package com.example.java.challenges.hackerrank.interview_prep_kit_1w.d3;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class ZigZag {

  @Test
  void test() throws Exception {
    String test_data = """
        1
        7
        1 2 3 4 5 6 7
        """;
    System.setIn(new ByteArrayInputStream(test_data.getBytes()));
    Main.main(null);
  }

  public class Main {

    public static void main(String[] args) throws java.lang.Exception {
      Scanner kb = new Scanner(System.in);
      int test_cases = kb.nextInt();
      for (int cs = 1; cs <= test_cases; cs++) {
        int n = kb.nextInt();
        int a[] = new int[n];
        for (int i = 0; i < n; i++) {
          a[i] = kb.nextInt();
        }
        findZigZagSequence(a, n);
      }
    }

    public static void findZigZagSequence(int[] a, int n) {

      Arrays.sort(a);
      int mid = (n + 1) / 2 - 1;
      int temp = a[mid];
      a[mid] = a[n - 1];
      a[n - 1] = temp;

      int st = mid + 1;
      int ed = n - 2;
      while (st <= ed) {
        temp = a[st];
        a[st] = a[ed];
        a[ed] = temp;
        st = st + 1;
        ed = ed - 1;
      }
      for (int i = 0; i < n; i++) {
        if (i > 0) {
          System.out.print(" ");
        }
        System.out.print(a[i]);
      }
      System.out.println();
    }
  }

}
