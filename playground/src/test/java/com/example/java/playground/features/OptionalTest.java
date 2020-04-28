package com.example.java.playground.features;

import com.example.java.playground.AbstractTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class OptionalTest extends AbstractTest {
    @Test
    void test() {
        var optionalNonNull = Optional.of("test");
        log.info("optionalNonNull :: filter: {}", optionalNonNull.filter(s -> s.length() > 2));
        log.info("optionalNonNull :: map: {}", optionalNonNull.map(s -> "value is '%s'".formatted(s)));

        Optional<String> optionalNull = Optional.ofNullable(null);
        Assertions.assertEquals(Optional.empty(), optionalNull);

        log.info("optional.orElse: {}", optionalNull.orElse("other"));
        log.info("optional.or: {}", optionalNull.or(() -> Optional.of(RandomStringUtils.random(10))));
        log.info("optional.orElseGet: {}", optionalNull.orElseGet(() -> "other"));
        try {
            log.info("optional.orElseThrow: {}", optionalNull.orElseThrow());
            Assertions.fail();
        } catch (Exception e) {
            //
        }

        log.info("optional.filter: {}", optionalNull.filter(v -> v.length() > 2));
        log.info("optional.map: {}", optionalNull.map(String::length));

        optionalNull.ifPresentOrElse(v -> log.info("present: {}", v), () -> log.info("empty"));
        optionalNonNull.ifPresentOrElse(v -> log.info("present: {}", v), () -> log.info("empty"));

        log.info("stream action (count): {} | {}", optionalNull.stream().count(), optionalNonNull.stream().count());

    }
}
