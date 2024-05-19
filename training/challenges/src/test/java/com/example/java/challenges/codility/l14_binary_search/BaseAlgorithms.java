package com.example.java.challenges.codility.l14_binary_search;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BaseAlgorithms {

  @Test
  void test() {
    assertThat(binarySearch(new int[]{1, 3, 5, 6, 7, 7, 8}, 6))
        .isEqualTo(3);
    assertThat(binarySearch(new int[]{1, 3, 5, 6, 7, 7, 8}, 9))
        .isEqualTo(-1);
  }

  public int binarySearch(int[] A, int x) {
    int n = A.length;
    int result = -1;
    for (int beg = 0, end = n - 1; beg < end; ) {
      int mid = (beg + end) / 2;
      int midV = A[mid];
      if (midV == x) {
        result = mid;
        break;
      } else if (midV > x) {
        end = mid - 1;
      } else {
        beg = mid + 1;
      }
    }
    return result;
  }

}
