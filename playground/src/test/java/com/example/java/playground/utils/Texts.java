package com.example.java.playground.utils;

import java.util.stream.Collectors;

public interface Texts {

    static String quoteLines(String text) {
        return text.lines()
                .map(Texts::quote)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    static String quote(String text) {
        return "'%s'".formatted(text);
    }

}
