package com.example.java.playground.features;

import com.example.java.playground.AbstractTest;
import com.example.java.playground.utils.Texts;
import org.junit.jupiter.api.Test;

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
    public void test_() {

    }
}
