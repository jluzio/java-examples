package com.example.java.playground.lib.junit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Month;
import java.util.EnumSet;
import java.util.stream.Stream;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ParameterizedTestTest {

  static class UnderTest {

    boolean isOdd(int number) {
      return (number & 1) == 1;
    }
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 3, 5, -3, 15, Integer.MAX_VALUE})
    // six numbers
  void isOdd(int number) {
    var underTest = new UnderTest();
    assertThat(underTest.isOdd(number)).isTrue();
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "  "})
  void isBlank_ShouldReturnTrueForNullOrBlankStrings(String input) {
    assertTrue(Strings.isBlank(input));
  }

  @ParameterizedTest
  @NullSource
  void isBlank_ShouldReturnTrueForNullInputs(String input) {
    assertTrue(Strings.isBlank(input));
  }
  @ParameterizedTest
  @EnumSource(value = Month.class, names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER"})
  void someMonths_Are30DaysLong(Month month) {
    final boolean isALeapYear = false;
    assertEquals(30, month.length(isALeapYear));
  }

  @ParameterizedTest
  @EnumSource(
      value = Month.class,
      names = {"APRIL", "JUNE", "SEPTEMBER", "NOVEMBER", "FEBRUARY"},
      mode = EnumSource.Mode.EXCLUDE)
  void exceptFourMonths_OthersAre31DaysLong(Month month) {
    final boolean isALeapYear = false;
    assertEquals(31, month.length(isALeapYear));
  }

  @ParameterizedTest
  @EnumSource(value = Month.class, names = ".+BER", mode = EnumSource.Mode.MATCH_ANY)
  void fourMonths_AreEndingWithBer(Month month) {
    EnumSet<Month> months =
        EnumSet.of(Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER);
    assertTrue(months.contains(month));
  }

  @ParameterizedTest
  @CsvSource({"test,TEST", "tEst,TEST", "Java,JAVA"})
  void toUpperCase_ShouldGenerateTheExpectedUppercaseValue(String input, String expected) {
    String actualValue = input.toUpperCase();
    assertEquals(expected, actualValue);
  }

  @ParameterizedTest
  @CsvFileSource(resources = "data.csv", numLinesToSkip = 1)
  void toUpperCase_ShouldGenerateTheExpectedUppercaseValueCSVFile(
      String input, String expected) {
    String actualValue = input.toUpperCase();
    assertEquals(expected, actualValue);
  }

  @ParameterizedTest
  @MethodSource("provideStringsForIsBlank")
  void isBlank_ShouldReturnTrueForNullOrBlankStrings(String input, boolean expected) {
    assertEquals(expected, Strings.isBlank(input));
  }

  private static Stream<Arguments> provideStringsForIsBlank() {
    return Stream.of(
        Arguments.of(null, true),
        Arguments.of("", true),
        Arguments.of("  ", true),
        Arguments.of("not blank", false)
    );
  }
}
