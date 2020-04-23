package com.example.java.playground.features;

import com.example.java.playground.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

public class VarKeywordTest extends AbstractTest {
    @Test
    void test() {
        var value = "123";

        log.info(value.substring(1));

        for (var v : List.of(1, 2, 3)) {
            log.info("v: {}", v);
        }
    }
}
