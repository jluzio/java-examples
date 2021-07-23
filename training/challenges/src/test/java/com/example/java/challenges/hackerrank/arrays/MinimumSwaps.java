package com.example.java.challenges.hackerrank.arrays;

import java.io.*;
import java.util.*;

/**
 * @see <a href="https://www.hackerrank.com/challenges/minimum-swaps-2">doc</a>
 */
public class MinimumSwaps {

    // Complete the minimumSwaps function below.
    static int minimumSwaps(int[] arr) {
        int swaps = 0;
        for (int i = 0; i < arr.length; i++) {
            int v = arr[i];
            int expectedV = i + 1;
            if (expectedV != v) {
                int expectedVPos = find(arr, i, expectedV);
                swap(arr, i, expectedVPos);
                swaps++;
            }
        }
        return swaps;
    }

    static void swap(int[] arr, int p1, int p2) {
        int v1 = arr[p1];
        int v2 = arr[p2];
        arr[p1] = v2;
        arr[p2] = v1;
    }

    static int find(int[] arr, int startPos, int v) {
        for (int i = startPos; i < arr.length; i++) {
            if (arr[i] == v) {
                return i;
            }
        }
        return -1;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        int[] arr = new int[n];

        String[] arrItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int arrItem = Integer.parseInt(arrItems[i]);
            arr[i] = arrItem;
        }

        int res = minimumSwaps(arr);

        bufferedWriter.write(String.valueOf(res));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}

