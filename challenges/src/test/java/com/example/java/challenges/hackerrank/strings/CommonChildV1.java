package com.example.java.challenges.hackerrank.strings;

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * @see <a href="https://www.hackerrank.com/challenges/common-child">challenge</a>
 */
public class CommonChildV1 {

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
                    s1.length() - 1,
                    s2.length() - 1,
                    new Integer[s1.length()][s2.length()]);
        }

        int lcs(char[] s1, char[] s2, int i, int j, Integer[][] memo) {
            if (i < 0 || j < 0)
                return 0;

            if (s1[i] == s2[j]) {
                int prevLcs = memoizedLcs(s1, s2, i - 1, j - 1, memo);
                memo[i][j] = prevLcs + 1;
                return memo[i][j];
            }

            return Math.max(
                    memoizedLcs(s1, s2, i - 1, j, memo),
                    memoizedLcs(s1, s2, i, j - 1, memo)
            );
        }

        int memoizedLcs(char[] s1, char[] s2, int i, int j, Integer[][] memo) {
            int lcs;
            if (i < 0 || j < 0) {
                lcs = 0;
            } else if (memo[i][j] != null) {
                lcs = memo[i][j];
            } else {
                lcs = lcs(s1, s2, i, j, memo);
                memo[i][j] = lcs;
            }
            return lcs;
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
