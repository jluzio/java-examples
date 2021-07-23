package com.example.java.challenges.hackerrank.strings;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.regex.*;
import java.util.stream.Collectors;

/**
 * @see <a href="https://www.hackerrank.com/challenges/ctci-making-anagrams">challenge</a>
 */
public class Anagrams {

    // Complete the makeAnagram function below.
    static int makeAnagram(String a, String b) {
        Map<Integer, Long> aCharCount = a.chars()
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<Integer, Long> bCharCount = b.chars()
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        long removedChars = 0;
        for (int c: aCharCount.keySet()) {
            long countA = aCharCount.getOrDefault(c, 0L);
            long countB = bCharCount.getOrDefault(c, 0L);
            removedChars += Math.abs(countA - countB);
        }
        aCharCount.keySet().forEach(bCharCount::remove);
        for (int c: bCharCount.keySet()) {
            long countA = aCharCount.getOrDefault(c, 0L);
            long countB = bCharCount.getOrDefault(c, 0L);
            removedChars += Math.abs(countA - countB);
        }
        return (int)removedChars;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String a = scanner.nextLine();

        String b = scanner.nextLine();

        int res = makeAnagram(a, b);

        bufferedWriter.write(String.valueOf(res));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
