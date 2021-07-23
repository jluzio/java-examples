package com.example.java.challenges.hackerrank.dicts_hashmaps;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * @see <a href="https://www.hackerrank.com/challenges/count-triplets-1">doc</a>
 */
public class CountTriplets {
    @Test
    void test() {
        List<Long> values = LongStream.of(1, 3, 3, 9, 9, 9, 27, 81).boxed().collect(toList());
        System.out.println(countTriplets(values, 3));
    }

    // Complete the countTriplets function below.
    static long countTriplets(List<Long> arr, long r) {
        Map<Long, Long> numberCounts = arr.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        List<Long> numbers = numberCounts.keySet().stream()
                .sorted()
                .collect(Collectors.toList());

        long triplets = 0;
        for (int i = 0; i < numbers.size(); i++) {
            long n1 = numbers.get(i);
            long n2 = n1 * r;
            long n3 = n2 * r;
            long countN1 = numberCounts.getOrDefault(n1, 0l);
            long countN2 = numberCounts.getOrDefault(n2, 0l);
            long countN3 = numberCounts.getOrDefault(n3, 0l);
            long currTriplets;
            if (r > 1) {
                currTriplets = countN1 * countN2 * countN3;
            } else {
                currTriplets = Math.max(countN1 - 2, 0)
                        + Math.max(countN1 - 3, 0)
                        + Math.max(countN1 - 4, 0);
            }
            triplets += currTriplets;
        }
        return triplets;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] nr = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(nr[0]);

        long r = Long.parseLong(nr[1]);

        List<Long> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Long::parseLong)
                .collect(toList());

        long ans = countTriplets(arr, r);

        bufferedWriter.write(String.valueOf(ans));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
