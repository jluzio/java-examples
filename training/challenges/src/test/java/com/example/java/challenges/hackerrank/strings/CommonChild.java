package com.example.java.challenges.hackerrank.strings;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.regex.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @see <a href="https://www.hackerrank.com/challenges/common-child">challenge</a>
 */
public class CommonChild {

    @Test
    void test() {
        System.out.printf("2 = %s%n", commonChild("HARRY", "SALLY"));
        System.out.printf("3 = %s%n", commonChild("SHINCHAN", "NOHARAAA"));
        System.out.printf("0 = %s%n", commonChild("AA", "BB"));
    }

    // Complete the commonChild function below.
    static int commonChild(String s1, String s2) {
        return new Algorithm().commonChild(s1, s2);
    }

    static class Algorithm {
        int commonChild(String s1, String s2) {
            return lcs(
                    s1.toCharArray(),
                    s2.toCharArray(),
                    s1.length(),
                    s2.length());
        }

        int lcs(char[] s1, char[] s2, int l1, int l2) {
            int longest = 0;
            int[] memo = new int[l2];
            IntFunction<Integer> memoVal = i -> i < 0 ? 0 : memo[i];

            for (int i = 0; i < l1; i++) {
                int prev = 0;
                for (int j = 0; j < l2; j++) {
                    int temp = memoVal.apply(j);
                    if (s1[i] == s2[j]) {
                        int currVal = prev + 1;
                        memo[j] = currVal;
                        if (currVal > longest) {
                            longest = currVal;
                        }
                    } else {
                        int prevLcs1 = memoVal.apply(j);
                        int prevLcs2 = memoVal.apply(j-1);
                        memo[j] = Math.max(prevLcs1, prevLcs2);
                    }
                    prev = temp;
                }
            }
            return longest;
        }
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s1 = scanner.nextLine();

        String s2 = scanner.nextLine();

        int result = commonChild(s1, s2);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
