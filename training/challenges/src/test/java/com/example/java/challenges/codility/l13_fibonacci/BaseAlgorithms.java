package com.example.java.challenges.codility.l13_fibonacci;

public class BaseAlgorithms {

  public int[] fibonacci(int n) {
    int[] values = new int[n];
    if (n >= 1) {
      values[1] = 1;
    }
    for (int i = 2; i < n; i++) {
      values[i] = values[i - 1] + values[i - 2];
    }
    return values;
  }

}
