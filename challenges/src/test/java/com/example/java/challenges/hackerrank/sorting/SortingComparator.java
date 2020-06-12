package com.example.java.challenges.hackerrank.sorting;

import java.util.*;

/**
 * @see <a href="https://www.hackerrank.com/challenges/ctci-comparator-sorting">doc</a>
 */
public class SortingComparator {
    class Checker implements Comparator<Player> {
        java.util.function.ToIntFunction<Player> getScore = value -> value.score;
        java.util.function.Function<Player, String> getName = value -> value.name;

        // complete this method
        public int compare(Player a, Player b) {
            return Comparator
                    .comparingInt(getScore).reversed()
                    .thenComparing(Comparator.comparing(getName))
                    .compare(a, b);
        }
    }

    class Player {
        String name;
        int score;

        Player(String name, int score) {
            this.name = name;
            this.score = score;
        }
    }
}
