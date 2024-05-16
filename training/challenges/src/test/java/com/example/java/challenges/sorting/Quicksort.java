package com.example.java.challenges.sorting;

public class Quicksort {

  public void quicksort(int[] A, int lo, int hi) {
    if (lo >= hi || lo < 0) {
      return;
    }

    int pivot = partition(A, lo, hi);

    quicksort(A, lo, pivot - 1); // Left side of pivot
    quicksort(A, pivot + 1, hi); // Right side of pivot
  }

  private int partition(int[] A, int lo, int hi) {
    int pivot = A[hi]; // Choose the last element as the pivot

    // Temporary pivot index
    int i = lo;

    for (int j = lo; j <= hi - 1; j++) {
      // If the current element is less than or equal to the pivot
      if (A[j] <= pivot) {
        // Swap the current element with the element at the temporary pivot index
        swap(A, i, j);
        i = i + 1;
      }
    }

    // Swap the pivot with the last element
    swap(A, hi, i);

    // the pivot index
    return i;
  }

  private void swap(int[] A, int i, int j) {
    int tmp = A[j];
    A[j] = A[i];
    A[i] = tmp;
  }
}
