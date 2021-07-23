package com.example.java.challenges.hackerrank.arrays;

import java.io.*;
        import java.math.*;
        import java.security.*;
        import java.text.*;
        import java.util.*;
        import java.util.concurrent.*;
        import java.util.regex.*;

/**
 * @see <a href="https://www.hackerrank.com/challenges/crush">doc</a>
 */
public class ArrayManipulation {

    // Complete the arrayManipulation function below.
    static long arrayManipulation(int n, int[][] queries) {
        long[] values = new long[n];
        for (int q = 0; q < queries.length; q++) {
            int[] query = queries[q];
            int a = query[0];
            int b = query[1];
            int k = query[2];
            if (k != 0) {
                values[a - 1] += k;
                if (b < n) {
                    values[b] -= k;
                }
            }
        }

        long max = Integer.MIN_VALUE;
        long curr = 0;
        for (int i = 0; i < values.length; i++) {
            curr += values[i];
            if (curr > max) {
                max = curr;
            }
        }
        return max;
    }

    static class Group {
        int a;
        int b;
        int value;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] nm = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nm[0]);

        int m = Integer.parseInt(nm[1]);

        int[][] queries = new int[m][3];

        for (int i = 0; i < m; i++) {
            String[] queriesRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < 3; j++) {
                int queriesItem = Integer.parseInt(queriesRowItems[j]);
                queries[i][j] = queriesItem;
            }
        }

        long result = arrayManipulation(n, queries);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
