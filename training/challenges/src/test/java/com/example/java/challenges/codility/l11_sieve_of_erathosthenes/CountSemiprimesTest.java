package com.example.java.challenges.codility.l11_sieve_of_erathosthenes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class CountSemiprimesTest {

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

    public int[] solution(int N, int[] P, int[] Q) {
      List<Integer> primes = primes(N);

      int[] semiprimes = new int[N + 1];
      for (int p1I = 0; p1I < primes.size(); p1I++) {
        int p1 = primes.get(p1I);
        for (int p2I = p1I; p2I < primes.size(); p2I++) {
          int p2 = primes.get(p2I);
          long semiprime = (long) p1 * p2;
          if (semiprime <= N) {
            semiprimes[(int)semiprime]++;
          } else {
            break;
          }
        }
      }
      for (int i = 1; i < semiprimes.length; i++) {
        semiprimes[i] += semiprimes[i - 1];
      }

      int[] result = new int[P.length];
      for (int i = 0; i < P.length; i++) {
        int from = P[i];
        int to = Q[i];
        result[i] = semiprimes[to] - (from > 0 ? semiprimes[from - 1] : 0);
      }
      return result;
    }

    private boolean[] sieve(int n) {
      boolean[] sieve = new boolean[n + 1];
      Arrays.fill(sieve, true);

      sieve[0] = sieve[1] = false;
      double sqrtN = Math.sqrt(n);
      for (int i = 2; i <= sqrtN; i++) {
        if (sieve[i]) {
          for (int k = i * i; k < n; k += i) {
            sieve[k] = false;
          }
        }
      }
      return sieve;
    }

    private List<Integer> primes(int n) {
      boolean[] sieve = sieve(n);
      List<Integer> primes = new ArrayList<Integer>();
      for (int i = 0; i < sieve.length; i++) {
        if (sieve[i]) {
          primes.add(i);
        }
      }
      return primes;
    }

  }

}
