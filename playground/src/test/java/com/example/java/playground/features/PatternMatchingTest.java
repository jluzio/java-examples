package com.example.java.playground.features;

import com.example.java.playground.AbstractTest;
import org.junit.jupiter.api.Test;

public class PatternMatchingTest extends AbstractTest {
    @Test
    void test_instance_of() {
        Object object = "   string-value   ";
        if (object instanceof String objAsStr) {
            log.info("object is string: {}", objAsStr.trim());
        } else {
            log.info("object is other type: {}", object);
        }
    }
}
