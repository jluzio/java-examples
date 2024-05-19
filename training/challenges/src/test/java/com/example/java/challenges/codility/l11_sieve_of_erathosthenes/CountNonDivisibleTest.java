package com.example.java.challenges.codility.l11_sieve_of_erathosthenes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CountNonDivisibleTest {

  Solution s = new Solution();

  @Test
  void test() {
    execute(new int[]{3, 1, 2, 3, 6});
    execute(new int[]{2});
  }

  void execute(int[] A) {
    var params = "%s".formatted(Arrays.toString(A));
    System.out.printf("%s :: %s%n",
        Arrays.toString(s.solution(A)),
        params
    );
  }

  class Solution {

    public int[] solution(int[] A) {
      Map<Integer, Integer> numberCountMap = new HashMap<Integer, Integer>();
      for (int a : A) {
        int count = numberCountMap.computeIfAbsent(a, v -> 0);
        numberCountMap.put(a, count + 1);
      }

      List<Integer> uniqueNumbers = new ArrayList<>(numberCountMap.keySet());
      Map<Integer, Integer> divisorCount = new HashMap<Integer, Integer>();
      for (int n : uniqueNumbers) {
        int currDivisors = 0;
        double sqrtN = Math.sqrt(n);
        for (int i = 1; i <= sqrtN; i++) {
          if (n % i == 0) {
            if (numberCountMap.containsKey(i)) {
              currDivisors += numberCountMap.get(i);
            }
            int otherDivisor = n / i;
            if (otherDivisor != i && numberCountMap.containsKey(otherDivisor)) {
              currDivisors += numberCountMap.get(otherDivisor);
            }
          }
        }
        divisorCount.put(n, currDivisors);
      }

      int[] result = new int[A.length];
      for (int i = 0; i < A.length; i++) {
        result[i] = A.length - divisorCount.get(A[i]);
      }

      return result;
    }

  }

}
