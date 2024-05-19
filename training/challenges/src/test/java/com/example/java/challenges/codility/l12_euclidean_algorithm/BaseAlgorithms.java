package com.example.java.challenges.codility.l12_euclidean_algorithm;

public class BaseAlgorithms {

  // greatest common divisor
  public int gcd(int a, int b, int res) {
    if (a == b) {
      return res * a;
    } else if (a % 2 == 0 && b % 2 == 0) {
      return gcd(a / 2, b / 2, res * 2);
    } else if (a % 2 == 0) {
      return gcd(a / 2, b, res);
    } else if (b % 2 == 0) {
      return gcd(a, b / 2, res);
    } else if (a > b) {
      return gcd(b, a - b, res);
    } else {
      return gcd(b - a, a, res);
    }
  }

}
