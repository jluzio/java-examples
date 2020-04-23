package com.example.java.playground.features;

import com.example.java.playground.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.StringJoiner;

public class StringJoinerTest extends AbstractTest {
    @Test
    public void test() {
        var joiner = new StringJoiner(", ", "{", "}");
        List.of("1", "2", "3").forEach(joiner::add);
        log.info(joiner.toString());
    }
}
