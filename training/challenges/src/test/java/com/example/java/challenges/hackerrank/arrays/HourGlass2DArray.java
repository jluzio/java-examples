package com.example.java.challenges.hackerrank.arrays;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

/**
 * @see <a href="https://www.hackerrank.com/challenges/2d-array">challenge</a>
 */
public class HourGlass2DArray {
    @Test
    void test() {
        String test = String.join(System.lineSeparator(), Arrays.asList(
                "-9 -9 -9 1 1 1",
                "0 -9 0 4 3 2",
                "-9 -9 -9 1 2 3",
                "0 0 8 6 6 0",
                "0 0 0 -2 0 0",
                "0 0 1 2 4 0"
        ));

        int[][] arr = new int[6][6];

        Scanner scanner = new Scanner(new StringReader(test));
        for (int i = 0; i < 6; i++) {
            String[] arrRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < 6; j++) {
                int arrItem = Integer.parseInt(arrRowItems[j]);
                arr[i][j] = arrItem;
            }
        }

        int result = hourglassSum(arr);
        System.out.println(result);
    }

    // Complete the hourglassSum function below.
    static int hourglassSum(int[][] arr) {
        Algorithm a = new Algorithm();
        return a.hourclassMax(arr);
    }

    static class Algorithm {
        Point2D[] positions = {
                new Point2D(0,0),
                new Point2D(1,0),
                new Point2D(2,0),
                new Point2D(1,1),
                new Point2D(0,2),
                new Point2D(1,2),
                new Point2D(2,2)
        };

        int hourclassMax(int[][] arr) {
            if (arr == null)
                return 0;

//            int maxX = arr.length;
//            int maxY = arr[0].length;
            int maxX = 6;
            int maxY = 6;

            int max = Integer.MIN_VALUE;
            for (int y = 0; y < maxY - 2; y++) {
                for (int x = 0; x < maxX - 2; x++) {
                    max = Math.max(max, hourclass(arr, x, y));
                }
            }
            return max;
        }

        int hourclass(int[][] arr, int x, int y) {
            return Arrays.stream(positions).reduce(
                    0,
                    (acc, point) -> acc + arr[y + point.y][x + point.x],
                    (v1, v2) -> v1 + v2
            );
        }

        class Point2D {
            int x;
            int y;
            public Point2D(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int[][] arr = new int[6][6];

        for (int i = 0; i < 6; i++) {
            String[] arrRowItems = scanner.nextLine().split(" ");
            scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

            for (int j = 0; j < 6; j++) {
                int arrItem = Integer.parseInt(arrRowItems[j]);
                arr[i][j] = arrItem;
            }
        }

        int result = hourglassSum(arr);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
