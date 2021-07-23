package com.example.java.challenges.hackerrank.strings;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * @see <a href="https://www.hackerrank.com/challenges/special-palindrome-again">challenge</a>
 */
public class SpecialStringAgain {

    // Complete the substrCount function below.
    static long substrCount(int n, String s) {
        List<CharCount> charCounts = new ArrayList<>();
        CharCount current = null;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (current == null || current.key != c) {
                current = new CharCount();
                current.key = c;
                current.count = 1;
                charCounts.add(current);
            } else if (current.key == c) {
                current.count++;
            }
        }

        int result = 0;
        for (int i = 0; i < charCounts.size(); i++) {
            CharCount charCount = charCounts.get(i);
            result += getPossibleSubstringsCount(charCount.count);
            if (i < charCounts.size() - 2) {
                CharCount middleCharCount = charCounts.get(i + 1);
                CharCount endCharCount = charCounts.get(i + 2);
                if (middleCharCount.count == 1 && endCharCount.key == charCount.key) {
                    result += Math.min(charCount.count, endCharCount.count);
                }
            }
        }
        return result;
    }

    static int getPossibleSubstringsCount(int n) {
        return IntStream.rangeClosed(1, n)
                .map(v -> n - v + 1)
                .sum();
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        String s = scanner.nextLine();

        long result = substrCount(n, s);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }

    static class CharCount {
        char key;
        int count;
    }
}
