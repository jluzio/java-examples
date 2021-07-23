package com.example.java.challenges;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class JsonTesting {
    public static void main(String[] args) {
        try {
            throw new RuntimeException("");
        } catch (Exception e) {
            System.out.println("caught");
        }
    }


    /*
     * Complete the 'getRecordsByAgeGroup' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER ageStart
     *  2. INTEGER ageEnd
     *  3. INTEGER bpDiff
     *
     *  https://jsonmock.hackerrank.com/api/medical_records
     */

    public static List<Integer> getRecordsByAgeGroup(int ageStart, int ageEnd, int bpDiff) {
        try {
            URL url = new URL(String.format("https://jsonmock.hackerrank.com/api/medical_records"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void getPage(int pageNum) {
        try (Scanner scanner = new Scanner(new URL("https://jsonmock.hackerrank.com/api/medical_records").openStream(),
                StandardCharsets.UTF_8.toString()))
        {
            String json = scanner.nextLine();
            System.out.println(json);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
