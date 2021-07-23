package com.example.java.challenges.hackerrank.strings;

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * @see <a href="https://www.hackerrank.com/challenges/common-child">challenge</a>
 */
public class CommonChildV2 {

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
                    new Integer[s1.length()][s2.length()]);
        }

        int lcs(char[] s1, char[] s2, Integer[][] memo) {
            int longest = 0;
            for (int i = 0; i < s1.length; i++) {
                for (int j = 0; j < s2.length; j++) {
                    if (s1[i] == s2[j]) {
                        int prevLcs;
                        if (i == 0 || j == 0) {
                            prevLcs = 0;
                        } else {
                            prevLcs = memo[i - 1][j - 1];
                        }
                        int currVal = prevLcs + 1;
                        memo[i][j] = currVal;
                        if (currVal > longest) {
                            longest = currVal;
                        }
                    } else {
                        int prevLcsI = i == 0 ? 0 : memo[i-1][j];
                        int prevLcsJ = j == 0 ? 0 : memo[i][j-1];
                        memo[i][j] = Math.max(prevLcsI, prevLcsJ);
                    }
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
