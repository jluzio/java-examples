package com.example.java.challenges.hackerrank.strings;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @see <a href="https://www.hackerrank.com/challenges/common-child">challenge</a>
 */
public class CommonChildOld {

    // Complete the commonChild function below.
    static int commonChild(String s1, String s2) {
        return new Algorithm().commonChild(s1, s2);
    }

    static class Algorithm {
        int commonChild(String s1, String s2) {
            int longest = 0;
            HashMap<Key, Integer> map = new HashMap<>();

            for (int i=0; i<s1.length(); i++) {
                for (int j=0; j<s2.length(); j++) {
                    Key k = new Key(i, j);
                    if (s1.charAt(i) == s2.charAt(j)) {
                        int value;
                        if (i == 0 || j == 0) {
                            value = 1;
                        } else {
                            Integer previous = map.getOrDefault(new Key(i, j - 1), 0);
                            value = previous + 1;
                        }
                        map.put(k, value);
                        if (value > longest) {
                            longest = value;
                        }
                    } else {
                        if (j == 0) {
                            map.put(k, 0);
                        } else {
                            Integer v = map.get(new Key(i, j - 1));
                            map.put(k, v);
                        }
                    }
                }
            }
            return longest;
        }

        int commonChildOld(String s1, String s2) {
            Map<Character, List<Integer>> indexOfMapS1 = getIndexOfMap(s1);
            Map<Character, List<Integer>> indexOfMapS2 = getIndexOfMap(s2);

            HashSet<Character> commonChars = new HashSet<>(indexOfMapS1.keySet());
            commonChars.retainAll(indexOfMapS2.keySet());

            HashSet<Character> toRemoveChars = new HashSet<>();
            toRemoveChars.removeAll(commonChars);

            toRemoveChars.forEach(indexOfMapS1::remove);
            toRemoveChars.forEach(indexOfMapS2::remove);

            return 0;
        }

        int commonChildS1(String s1, String s2, Map<Integer, List<Integer>> indexOfMapS1, Map<Integer, List<Integer>> indexOfMapS2) {
            int currentS2Index = -1;
            for (int i = 0; i < s1.length(); i++) {
                int c = s1.charAt(i);

                List<Integer> indexOfCInS2 = indexOfMapS2.get(c);
                Optional<Integer> nextIndexS2 = indexOfCInS2.stream().filter(idx -> idx > currentS2Index).findFirst();
                if (nextIndexS2.isPresent()) {

                }
            }
            return 0;
        }

        Map<Character, List<Integer>> getIndexOfMap(String string) {
            Map<Character, List<Integer>> map = new LinkedHashMap<>();
            for (int i = 0; i < string.length(); i++) {
                char c = string.charAt(i);
                List<Integer> indexOfs = map.computeIfAbsent(c, ArrayList::new);
                indexOfs.add(i);
                map.putIfAbsent(c, indexOfs);
            }
            return map;
        }

        Collection<Character> getCommonCharacters(Collection<Character> chars1, Collection<Character> chars2) {
            chars1.retainAll(chars2);
            return chars1;
        }

    }

    static class Key {
        int i;
        int j;

        public Key() {
        }

        public Key(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            return i == key.i &&
                    j == key.j;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j);
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
