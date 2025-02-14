package com.example.java.playground.features.lang;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.java.playground.utils.Strings;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class StringFunctionsTest {

  @Test
  void test_text_block() {
    var text = """
        line 1
          line 2   
            line 3
        """;
    log.info(Strings.toTextBlock(text.lines(), Strings::quote));
  }

  @Test
  void test_formatted() {
    log.info("formatted: {}", "2: %2$s | 1: %1$s".formatted("v-1", "v-2"));
  }

  @Test
  void test_lines() {
    var text = """
        line 1
          line 2   
            line 3
        """;
    log.info("lines 2: {}", text.lines().limit(2).toList());
  }

  @Test
  void strip() {
    var text = new StringJoiner(System.lineSeparator())
        .add("line 1")
        .add("line 2  ")
        .add("  line 3")
        .add("  line 4  ")
        .toString();

    log.info(Strings.toTextBlock(text.strip().lines(), Strings::quote));
    log.info(Strings.toTextBlock(text.stripIndent().lines(), Strings::quote));
    log.info(Strings.toTextBlock(text.stripLeading().lines(), Strings::quote));
    log.info(Strings.toTextBlock(text.stripTrailing().lines(), Strings::quote));
  }

  @Test
  void test_is_empty() {
    log.info("e: {}", "  ".isBlank());
  }

  @Test
  void test_repeat() {
    log.info("v: {}", "pete".repeat(3));
  }

  @Test
  void test_format() {
    var expectedFormattedNumber = "%.2f".formatted(123.46);

    // note: total chars 7, rounded using java.math.RoundingMode#HALF_UP
    assertThat("%7.2f".formatted(123.456789))
        .isEqualTo(" %s".formatted(expectedFormattedNumber))
        .hasSize(7);

    assertThat("%-7.2f".formatted(123.456789))
        .isEqualTo("%s ".formatted(expectedFormattedNumber))
        .hasSize(7);

    assertThat("%5d".formatted(123))
        .isEqualTo("  123")
        .hasSize(5);

    assertThat("%1$s...%1$s".formatted("seeing double"))
        .isEqualTo("seeing double...seeing double");
  }
}
