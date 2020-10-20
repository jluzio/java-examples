package com.example.java.playground.utils;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Strings {

  static <T> String toTextBlock(Stream<T> values) {
    return toTextBlock(values, Objects::toString);
  }

  static <T> String toTextBlock(Stream<T> values, Function<T, String> toString) {
    return values.map(toString).collect(Collectors.joining(System.lineSeparator()));
  }

  static String quote(String string) {
    return quote(string, "'");
  }

  static String quote(String text, String quote) {
    return String.format("%s%s%s", quote, text, quote);
  }

}
