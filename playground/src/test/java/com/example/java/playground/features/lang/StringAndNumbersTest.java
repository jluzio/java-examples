package com.example.java.playground.features.lang;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class StringAndNumbersTest {

  @Test
  void stringDigits() {
    var numberAsString = "123456789";
    var digits = IntStream.rangeClosed(1, 9).boxed().toList();

    // number -> string
    String numberAsStringTest1 = IntStream.rangeClosed(1, 9)
        .mapToObj(String::valueOf)
        .collect(Collectors.joining());
    assertThat(numberAsStringTest1)
        .isEqualTo(numberAsString);

    String numberAsStringTest2 = IntStream.rangeClosed(1, 9)
        .boxed()
        .reduce("", (acc, value) -> acc + value, (acc1, acc2) -> null);
    assertThat(numberAsStringTest2)
        .isEqualTo(numberAsString);

    // string -> number
    var digitsTest1 = numberAsString.chars()
        .mapToObj(Character::getNumericValue)
        .toList();
    assertThat(digitsTest1)
        .isEqualTo(digits);

    var digitsTest2 = numberAsString.chars()
        .mapToObj(v -> Character.digit(v, Character.MAX_RADIX))
        .toList();
    assertThat(digitsTest2)
        .isEqualTo(digits);

    var digitsTest3 = numberAsString.chars()
        .mapToObj(v -> Integer.valueOf(String.valueOf((char) v)))
        .toList();
    assertThat(digitsTest3)
        .isEqualTo(digits);

    // Character.toString() since Java 11
    var digitsTest4 = numberAsString.chars()
        .mapToObj(v -> Integer.valueOf(Character.toString(v)))
        .toList();
    assertThat(digitsTest4)
        .isEqualTo(digits);
  }

}
