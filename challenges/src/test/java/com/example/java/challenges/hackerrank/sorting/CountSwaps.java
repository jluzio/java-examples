package com.example.java.challenges.hackerrank.sorting;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

/**
 * @see <a href="https://www.hackerrank.com/challenges/ctci-bubble-sort/problem">doc</a>
 */
public class CountSwaps {

    // Complete the countSwaps function below.
    static void countSwaps(int[] a) {
        int n = a.length;
        int swaps = 0;
        for (int i = 0; i < n; i++) {

            for (int j = 0; j < n - 1; j++) {
                // Swap adjacent elements if they are in decreasing order
                if (a[j] > a[j + 1]) {
                    swap(a, j, j + 1);
                    swaps++;
                }
            }
        }
        System.out.println(String.format("Array is sorted in %s swaps.", swaps));
        System.out.println(String.format("First Element: %s", a[0]));
        System.out.println(String.format("Last Element: %s", a[a.length-1]));
    }

    static void swap(int[] a, int p1, int p2) {
        int v1 = a[p1];
        int v2 = a[p2];
        a[p1] = v2;
        a[p2] = v1;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] a = new int[n];

        String[] aItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int aItem = Integer.parseInt(aItems[i]);
            a[i] = aItem;
        }

        countSwaps(a);

        scanner.close();
    }
}
