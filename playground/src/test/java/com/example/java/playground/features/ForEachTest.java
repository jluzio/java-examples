package com.example.java.playground.features;

import com.example.java.playground.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ForEachTest extends AbstractTest {
    @Test
    void test() {
        Stream.of(1, 2, 3).forEach(v -> log.info("v: {}", v));
        Arrays.asList(1, 2, 3).forEach(v -> log.info("v: {}", v));
        List.of(1, 2, 3).forEach(v -> log.info("v: {}", v));
    }
}
