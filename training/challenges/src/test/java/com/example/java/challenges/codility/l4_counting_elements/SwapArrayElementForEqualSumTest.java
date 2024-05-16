package com.example.java.challenges.codility.l4_counting_elements;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

class SwapArrayElementForEqualSumTest {

  Solution s = new Solution();

  @Test
  void test() {
    int A[] = {4, 1, 2, 1, 1, 2};
    int n = A.length;
    int B[] = {3, 6, 3, 3};
    int m = B.length;

    // Call to function
    s.findSwapValues(A, n, B, m);
  }

  class Solution {

    // Function to prints elements to be swapped
    void findSwapValues(int A[], int n, int B[], int m) {
      // Call for sorting the arrays
      Arrays.sort(A);
      Arrays.sort(B);

      // Note that target can be negative
      int target = getTarget(A, n, B, m);

      // target 0 means, answer is not possible
      if (target == 0) {
        return;
      }

      int i = 0, j = 0;
      while (i < n && j < m) {
        int diff = A[i] - B[j];
        if (diff == target) {
          System.out.println(A[i] + " " + B[i]);
          return;
        } else if (diff < target) { // Look for a greater value in A[]
          i++;
        } else { // Look for a greater value in B[]
          j++;
        }
      }
    }

    // Function to calculate : a - b = (sumA - sumB) / 2
    int getTarget(int A[], int n, int B[], int m) {
      // Calculation of sums from both arrays
      int sum1 = getSum(A, n);
      int sum2 = getSum(B, m);

      // diff must be even, so the swap can happen
      int diff = sum1 - sum2;
      if (diff % 2 != 0) {
        return 0;
      }
      return (diff / 2);
    }

    int getSum(int X[], int n) {
      int sum = 0;
      for (int i = 0; i < n; i++) {
        sum += X[i];
      }
      return sum;
    }
  }
}
