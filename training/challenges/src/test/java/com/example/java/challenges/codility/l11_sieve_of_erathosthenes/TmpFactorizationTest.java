package com.example.java.challenges.codility.l11_sieve_of_erathosthenes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class TmpFactorizationTest {

  Solution s = new Solution();

  @Test
  void test() {
//    execute(20, new int[]{1, 4, 16}, new int[]{26, 10, 20});
    execute(26, new int[]{1, 4, 16}, new int[]{26, 10, 20});
  }

  void execute(int N, int[] P, int[] Q) {
    var params = "%s".formatted(N, Arrays.toString(P), Arrays.toString(Q));
    System.out.printf("%s :: %s%n",
        Arrays.toString(s.solution(N, P, Q)),
        params
    );
  }

  class Solution {

    // NOT A REAL EXERCISE
    // NOT WORKING
    public int[] solution(int N, int[] P, int[] Q) {
      int[] f = arrayF(N);
      List<Integer> primeFactors = factorization(N, f);

      int[] result = new int[P.length];
      for (int i = 0; i < P.length; i++) {
        int from = P[i];
        int to = Q[i];
        int count = 0;
        for (int p = 0; p < primeFactors.size(); p++) {
          int prime1 = primeFactors.get(p);
          int otherDivisor = N / prime1;
          if (!primeFactors.contains(otherDivisor)) {
            continue;
          }
          if (prime1 >= from && otherDivisor <= to) {
            count++;
          }
        }
        result[i] = count;
      }
      return result;
    }

    private List<Integer> factorization(int x, int[] f) {
      List<Integer> primeFactors = new ArrayList<>();
      for (; f[x] > 0; x /= f[x]) {
        primeFactors.add(f[x]);
      }
      primeFactors.add(x);
      return primeFactors;
    }

    private int[] arrayF(int n) {
      int[] f = new int[n + 1];
      double sqrtN = Math.sqrt(n);
      for (int i = 2; i <= sqrtN; i++) {
        if (f[i] == 0) {
          for (int k = i * i; k <= n; k += i) {
            if (f[k] == 0) {
              f[k] = i;
            }
          }
        }
      }
      return f;
    }

  }

}
