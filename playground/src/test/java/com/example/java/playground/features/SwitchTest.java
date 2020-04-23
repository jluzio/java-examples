package com.example.java.playground.features;

import com.example.java.playground.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SwitchTest extends AbstractTest {
    @Test
    public void test() {
        var switchVal = "test";
        var returnValue = switch (switchVal) {
            case "x", "y" -> 1;
            case "z" -> 2;
            case "test" -> {
                log.info("entering block");
                yield 99;
            }
            default -> 0;
        };
        log.info("Result was: %s".formatted(returnValue));
    }
}
