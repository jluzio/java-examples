package com.example.java.playground.features;

import com.example.java.playground.AbstractTest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTest extends AbstractTest {
    @Test
    void test_find() {
//        Supplier<Stream<Integer>> values = () -> Stream.of(1, 2, 3, 4, 5);
        Supplier<Stream<Integer>> values = () -> List.of(1, 2, 3, 4, 5).stream();

        log.info("findAny: {}", values.get().findAny());
        log.info("findFirst: {}", values.get().findFirst());
        log.info("findFirst empty: {}", Stream.empty().findFirst().isPresent());

        log.info("filter: {}", values.get().filter(v -> v > 2).collect(Collectors.toList()));

        log.info("allMatch: {}", values.get().allMatch(v -> v > 2));
        log.info("anyMatch: {}", values.get().anyMatch(v -> v > 2));
        log.info("noneMatch: {}", values.get().noneMatch(v -> v > 2));
    }

    @Test
    void test_optional() {
        Optional<String> optionalNull = Optional.ofNullable(null);
        Assertions.assertEquals(Optional.empty(), optionalNull);
        log.info("optionalNull :: filter: {}", optionalNull.filter(s -> s.length() > 2));
        log.info("optionalNull :: map: {}", optionalNull.map(s -> "value is '%s'".formatted(s)));

        var optionalNonNull = Optional.of("test");
        log.info("optionalNonNull :: filter: {}", optionalNonNull.filter(s -> s.length() > 2));
        log.info("optionalNonNull :: map: {}", optionalNonNull.map(s -> "value is '%s'".formatted(s)));
    }

    @Test
    void test_create_stream() {
        IntStream as1 = Arrays.stream(new int[]{1, 2, 3});
        DoubleStream as2 = Arrays.stream(new double[]{1, 2, 3});
        Stream<Double> as3 = Arrays.stream(new Double[]{1d, 2d, 3d});

        var random = new Random();
        log.info("randoms: {}", random.ints().limit(3).boxed().collect(Collectors.toList()));
    }

    @Data
    @AllArgsConstructor
    class IterateExampleData {
        @NonNull private List<Integer> values;
        private int index;
    }

    @Test
    void test_iterate() {
        var values = IntStream.range(0, 10).boxed().collect(Collectors.toList());
        var iterateData = new IterateExampleData(values, 0);

        for (val value : List.of(1, 2, 3)) {

        }

        Predicate<Integer> hasNext = value -> iterateData.getIndex() + 1 < iterateData.getValues().size();

        UnaryOperator<Integer> getNext = value -> {
            if (hasNext.test(value)) {
                iterateData.setIndex(iterateData.getIndex() + 1);
                return iterateData.getValues().get(iterateData.getIndex());
            }
            return null;
        };

        Stream.iterate(values.get(0), getNext).limit(12).forEach(v -> log.info("Stream.iterate[limit=12]: {}", v));
        Stream.iterate(values.get(0), hasNext, getNext).forEach(v -> log.info("Stream.iterate[hasNext]: {}", v));
    }

    @Test
    void test_collectors() {
        var intSum = IntStream.of(1, 2, 3)
                .filter(v -> v >= 2)
                .sum();
        log.info("intSum: {}", intSum);

        var intSum2 = Stream.of(1, 2, 3)
                .filter(v -> v >= 2)
                .collect(Collectors.summarizingInt(v -> v));
        log.info("intSum2: {}", intSum2);
    }
}
