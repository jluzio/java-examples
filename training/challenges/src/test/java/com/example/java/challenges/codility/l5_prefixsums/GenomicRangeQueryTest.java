package com.example.java.challenges.codility.l5_prefixsums;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class GenomicRangeQueryTest {

  Solution s = new Solution();

  @Test
  void test() {
    execute("CAGCCTA", new int[]{2, 5, 0}, new int[]{4, 5, 6});
    execute("A", new int[]{0}, new int[]{0});
    execute("C", new int[]{0}, new int[]{0});
  }

  @Test
  void test_performance() {
    var pattern = "GCGCT";
    var patternSize = pattern.length();
    var from = 1;
    var to = 4;
    Supplier<IntStream> queryStreamSupplier = () -> IntStream.rangeClosed(1, 50_000 / patternSize)
        .map(v -> v * patternSize);
    execute(
        pattern.repeat(100_000 / patternSize),
        queryStreamSupplier.get().map(v -> from).toArray(),
        queryStreamSupplier.get().map(v -> v + to).toArray());
  }

  void execute(String S, int[] P, int[] Q) {
    var params = "%s | %s | %s".formatted(S, Arrays.toString(P), Arrays.toString(Q));
    System.out.printf("%s :: %s%n",
        Arrays.toString(s.solution(S, P, Q)),
        params
    );
  }

  class Solution {

    public int[] solution(String S, int[] P, int[] Q) {
      int[] output = new int[P.length];
      char[] chars = S.toCharArray();

      // arrays of count of letters from 0-to-index
      // number of occurrences in to-from is "countTo" - "countFrom" (countFrom = count[from -1] or 0 if from == 0)
      int[] aCount = new int[S.length()];
      int[] cCount = new int[S.length()];
      int[] gCount = new int[S.length()];
      int[] tCount = new int[S.length()];
      char c;
      for (int i = 0; i < chars.length; i++) {
        if (i > 0) {
          aCount[i] = aCount[i - 1];
          cCount[i] = cCount[i - 1];
          gCount[i] = gCount[i - 1];
          tCount[i] = tCount[i - 1];
        }

        c = chars[i];
        if (c == 'A') {
          aCount[i]++;
        } else if (c == 'C') {
          cCount[i]++;
        } else if (c == 'G') {
          gCount[i]++;
        } else if (c == 'T') {
          tCount[i]++;
        }
      }

      for (int i = 0, from, to; i < P.length; i++) {
        from = P[i];
        to = Q[i];
        // check if it has the character by checking the sums of occurrences
        if (hasChar(aCount, from, to)) {
          output[i] = 1;
        } else if (hasChar(cCount, from, to)) {
          output[i] = 2;
        } else if (hasChar(gCount, from, to)) {
          output[i] = 3;
        } else if (hasChar(tCount, from, to)) {
          output[i] = 4;
        }
      }
      return output;
    }

    // check if it has the character by checking the sums of occurrences
    private boolean hasChar(int[] counts, int from, int to) {
      int countsTo = counts[to];
      int countsFrom = from > 0 ? counts[from - 1] : 0;
      return countsTo - countsFrom > 0;
    }
  }
}
