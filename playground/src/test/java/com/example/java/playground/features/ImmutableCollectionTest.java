package com.example.java.playground.features;

import com.example.java.playground.AbstractTest;
import org.checkerframework.common.value.qual.IntRange;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Map.*;

public class ImmutableCollectionTest extends AbstractTest {
    @Test
    void test() {
        log.info("v: {}", List.of(1, 2, 3));
        log.info("v: {}", Set.of(1, 2, 3));
        log.info("v: {}", Map.of(1, "val-1", 2, "val-2"));
        log.info("v: {}", Map.ofEntries(entry(1, "val-1"), entry(2, "val-2")));

        var list = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        var list2 = List.copyOf(list);
        list.add(11);

        log.info("l: {} | l2: {}", list, list2);
    }
}
