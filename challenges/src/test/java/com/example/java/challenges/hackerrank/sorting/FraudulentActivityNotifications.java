package com.example.java.challenges.hackerrank.sorting;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;
import java.util.stream.IntStream;

/**
 * @see <a href="https://www.hackerrank.com/challenges/fraudulent-activity-notifications">doc</a>
 */
public class FraudulentActivityNotifications {
    @Test
    void test() {
//        System.out.println(activityNotifications(IntStream.of(2, 3, 4, 2, 3, 6, 8, 4, 5).toArray(), 5));
        System.out.println(activityNotifications(IntStream.of(10, 20 ,30 ,40, 50).toArray(), 3));
    }

    // Complete the activityNotifications function below.
    static int activityNotifications(int[] expenditure, int d) {
        int[] values = new int[d];
        int alerts = 0;
        for (int i = 0; i < expenditure.length; i++) {
            if (i + 1 == d) {
                Arrays.sort(values);
            }
            else if (i + 1 > d) {
                Arrays.sort(values);
                double median = getMedian(values);
                if (expenditure[i] >= median * 2) {
                    alerts++;
                }
                int outValue = expenditure[i - d];
                int outIndex = Arrays.binarySearch(values, outValue);
                values[outIndex] = expenditure[i];
            }
            else {
                values[i] = expenditure[i];
            }
        }
        return alerts;
    }

    static void addToSortedArray(int[] arr, int outValue, int inValue) {
        Integer currentReplacing = null;
        for (int i = 0; i < arr.length; i++) {
            int v = arr[i];
            if (v < inValue) {
//                currentReplacing = ;
            }
            if (v == outValue) {

            }
        }
    }

    static double getMedian(int[] values) {
        int length = values.length;
        int middlePos = length / 2;
        if (length % 2 == 0) {
            return 1d * (values[middlePos] + values[middlePos + 1]) / 2;
        } else {
            return values[middlePos];
        }
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] nd = scanner.nextLine().split(" ");

        int n = Integer.parseInt(nd[0]);

        int d = Integer.parseInt(nd[1]);

        int[] expenditure = new int[n];

        String[] expenditureItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            int expenditureItem = Integer.parseInt(expenditureItems[i]);
            expenditure[i] = expenditureItem;
        }

        int result = activityNotifications(expenditure, d);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedWriter.close();

        scanner.close();
    }
}
