package com.example.java.challenges.codility;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class MinCharsTest {

  @Test
  void test() {
    System.out.println(new Solution().solution("axxz", "yzwy"));
  }

  static class Solution {
    public int solution(String P, String Q) {

      int[] letterFrequency = new int[26];
      for (int i=0; i<P.length(); i++) {
        char charP = P.charAt(i);
        char charQ = Q.charAt(i);

        letterFrequency[getIndex(charP)]++;
        if (charP != charQ) {
          letterFrequency[getIndex(charQ)]++;
        }
      }

      char[] chars = new char[P.length()];
      for (int i=0; i<P.length(); i++) {
        char charP = P.charAt(i);
        char charQ = Q.charAt(i);
        int frequencyP = letterFrequency[getIndex(charP)];
        int frequencyQ = letterFrequency[getIndex(charQ)];
        if (frequencyP > frequencyQ) {
          chars[i] = charP;
        } else if (frequencyP == frequencyQ) {
          int remainingFrequencyP = remainingFrequency(P, charP, i);
          int remainingFrequencyQ = remainingFrequency(Q, charQ, i);
          if (remainingFrequencyP > remainingFrequencyQ) {
            chars[i] = charP;
          } else {
            chars[i] = charQ;
          }
        } else {
          chars[i] = charQ;
        }
      }

      Arrays.sort(chars);
      int count = 1;
      for (int i=1; i<chars.length; i++) {
        if (chars[i] != chars[i-1])
          count++;
      }

      return count;
    }

    private int getIndex(int c) {
      return c - 'a';
    }

    private int remainingFrequency(String string, char c, int fromIndex) {
      int count = 0;
      for (int i = fromIndex; i < string.length() ; i++) {
        if (c == string.charAt(i)) {
          count++;
        }
      }
      return count;
    }
  }

}
