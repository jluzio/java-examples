package com.example.java.challenges.hackerrank.strings;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.regex.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * @see <a href="https://www.hackerrank.com/challenges/sherlock-and-valid-string">challenge</a>
 */
public class SherlockAndValidString {
    // Complete the isValid function below.
    static String isValid(String s) {
        Map<Integer, Long> charOccurs = s.chars()
                .mapToObj(c -> c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Map<Long, Long> countOccurs = charOccurs.entrySet().stream()
                .collect(groupingBy(Map.Entry::getValue, Collectors.counting()));

        switch (countOccurs.size()) {
//            case 0:
            case 1:
                return "YES";
            case 2:
                ArrayList<Long> counts = new ArrayList<>(countOccurs.keySet());
                boolean countDiff = Math.abs(counts.get(0) - counts.get(1)) == 1;
                return countOccurs.containsValue(1L) && countDiff ? "YES" : "NO";
            default:
                return "NO";
        }
    }

    @Test
    void test() {
        System.out.println(isValid("abcdefghhgfedecba"));
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s = scanner.nextLine();

        String result = isValid(s);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
