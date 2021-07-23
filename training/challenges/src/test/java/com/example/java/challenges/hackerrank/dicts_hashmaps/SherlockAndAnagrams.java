package com.example.java.challenges.hackerrank.dicts_hashmaps;
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

/**
 * @see <a href="https://www.hackerrank.com/challenges/sherlock-and-anagrams">doc</a>
 */
public class SherlockAndAnagrams {
    @Test
    void test() {
        System.out.println(sherlockAndAnagrams("ifailuhkqq"));
    }

    // Complete the sherlockAndAnagrams function below.
    static int sherlockAndAnagrams(String s) {
        int anagrams = 0;
        for (int len = 1; len <= s.length() - 1; len++) {
            for (int i = 0; i < s.length() - len + 1; i++) {
                String s1 = s.substring(i, i + len);
                Map<Integer, Long> charGroupsS1 = s1.chars()
                        .boxed()
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
                for (int j = i + 1; j < s.length() - len + 1; j++) {
                    String s2 = s.substring(j, j + len);
                    Map<Integer, Long> charGroupsS2 = s2.chars()
                            .boxed()
                            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
                    if (charGroupsS1.equals(charGroupsS2)) {
                        anagrams++;
                    }
                }
            }
        }
        return anagrams;
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int q = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int qItr = 0; qItr < q; qItr++) {
            String s = scanner.nextLine();

            int result = sherlockAndAnagrams(s);

            bufferedWriter.write(String.valueOf(result));
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }
}
