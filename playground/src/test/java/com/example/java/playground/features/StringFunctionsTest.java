package com.example.java.playground.features;

import com.example.java.playground.AbstractTest;
import com.example.java.playground.utils.Texts;
import org.junit.jupiter.api.Test;

import java.util.StringJoiner;
import java.util.stream.Collectors;

public class StringFunctionsTest extends AbstractTest {
    @Test
    public void test_text_block() {
        var text = """
                line 1
                  line 2   
                    line 3
                """;
        log.info(Texts.quoteLines(text));
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

        log.info(Texts.quoteLines(text.strip()));
        log.info(Texts.quoteLines(text.stripIndent()));
        log.info(Texts.quoteLines(text.stripLeading()));
        log.info(Texts.quoteLines(text.stripTrailing()));

    }
}
