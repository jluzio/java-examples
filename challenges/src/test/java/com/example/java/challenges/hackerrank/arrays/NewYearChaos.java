package com.example.java.challenges.hackerrank.arrays;

import java.io.*;
        import java.math.*;
        import java.security.*;
        import java.text.*;
        import java.util.*;
        import java.util.concurrent.*;
        import java.util.regex.*;
import java.util.stream.IntStream;

/**
 * <a href="https://www.hackerrank.com/challenges/new-year-chaos">doc</a>
 */
public class NewYearChaos {

    // Complete the minimumBribes function below.
    static void minimumBribes(int[] q) {
        int[] curr_q = IntStream.rangeClosed(1, q.length).toArray();
        int bribes = 0;

        for (int i = 0; i < q.length; i++) {
            int person = q[i];
            int expectedPerson = curr_q[i];
            if (person != expectedPerson) {
                int dislocatedPerson = expectedPerson;
                int briberPos = findBriber(person, curr_q, i);

                int currentBribes = briberPos - i;
                if (currentBribes > 2) {
                    System.out.println("Too chaotic");
                    return;
                }
                bribes += currentBribes;
                for (int cq = briberPos; cq > i; cq--) {
                    swapLeft(curr_q, cq);
                }
            }
        }
        System.out.println(bribes);
    }

    static int findBriber(int briber, int q[], int startingPos) {
        int briberPos = startingPos;
        while (briberPos < q.length && q[briberPos] != briber) {
         briberPos++;
        }
        return briberPos;
    }

    static void swapLeft(int q[], int pos) {
        int right = q[pos];
        int left = q[pos - 1];

        q[pos - 1] = right;
        q[pos] = left;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int t = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int tItr = 0; tItr < t; tItr++) {
            int n = scanner.nextInt();
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            int[] q = new int[n];

            String[] qItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int i = 0; i < n; i++) {
                int qItem = Integer.parseInt(qItems[i]);
                q[i] = qItem;
            }

            minimumBribes(q);
        }

        scanner.close();
    }
}
