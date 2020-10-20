package com.example.java.playground.features;

import com.example.java.playground.AbstractTest;
import com.example.java.playground.utils.Strings;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class StringFunctionsTest extends AbstractTest {

  @Test
  public void test_text_block() {
    var text = """
        line 1
          line 2   
            line 3
        """;
    log.info(Strings.toTextBlock(text.lines(), Strings::quote));
  }

  @Test
  public void test_formatted() {
    log.info("formatted: {}", "2: %2$s | 1: %1$s".formatted("v-1", "v-2"));
  }

  @Test
  public void test_lines() {
    var text = """
        line 1
          line 2   
            line 3
        """;
    log.info("lines 2: {}", text.lines().limit(2).collect(Collectors.toList()));
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
}
