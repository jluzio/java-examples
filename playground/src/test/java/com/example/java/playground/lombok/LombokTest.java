package com.example.java.playground.lombok;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Cleanup;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class LombokTest {
    @Test
    void test_log() {
        log.info("Log provided by @Slf4j");
    }

    @Test
    void test_val() {
        val x = "123";
        log.info("val.1: {}", x.charAt(2));
        // Compiler error
//        x = "3333";
    }

    // equivalent to var in Java 10
    // and if using java 10+, we can't use it from lombok
    @Test
    void test_var() {
        var x = "123";
        log.info("val.1: {}", x.charAt(2));
        x = "3333";
        log.info("val.1: {}", x.charAt(2));
    }

    @Test
    void test_cleanup() throws IOException {
        @Cleanup Closeable closeable = new Closeable() {
            @Override
            public void close() throws IOException {
                log.info("Cleanup :: close called");
            }
        };
        log.info("Cleanup start :: {}", closeable);
    }

//    @Test
    void test_sneaky_throws() {
        try {
            sneaky_throws();
            Assertions.fail("expected exception");
        } catch (Exception e) {
            Assertions.assertEquals(IOException.class, e.getClass());
        }
    }

//    @SneakyThrows
    // @SneakyThrows from Lombok 1.18.12 is incompatible with Java 15?
    void sneaky_throws() throws IOException {
        throw new IOException("test");
    }

    @Test
    void test_sync() {
        var list = IntStream.range(0, 10).boxed().collect(Collectors.toList());

        Consumer<Integer> print = v -> log.info("print: {}", v);

        log.info("default");
        list.stream().forEach(print);
        log.info("parallel");
        list.stream().parallel().forEach(print);

        log.info("call sync parallel");
        list.stream().parallel().forEach(v -> sync());

        log.info("call non_sync parallel");
        list.stream().parallel().forEach(v -> non_sync());

    }

    int syncValue = 0;
    @Synchronized
    void sync() {
        syncValue++;
        log.info("syncValue: {}", syncValue);
    }
    int nonSyncValue = 0;
    void non_sync() {
        nonSyncValue++;
        log.info("nonSyncValue: {}", nonSyncValue);
    }

    class LazyGetter {
        @Getter(lazy = true)
        private final List<Integer> list = expensiveCall();

//        @SneakyThrows
        private List<Integer> expensiveCall() {
            log.info("expensiveCall");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return List.of(1, 2, 3);
        }
    }

    @Test
    void test_lazy_getter() {
        var data = new LazyGetter();
        log.info("list: {}", data.getList());
        log.info("list: {}", data.getList());
    }

    class NonNullBean {
        @NonNull @Getter @Setter
        private String value;
    }

    @Test
    void test_non_null() {
        var data = new NonNullBean();
        data.setValue("value1");
        try {
            data.setValue(null);
            Assertions.fail();
        } catch (NullPointerException e) {
            // expected
        }

        callNonNull("nn");
        try {
            callNonNull(null);
            Assertions.fail();
        } catch (NullPointerException e) {
            // expected
        }
    }

    private void callNonNull(@NonNull String value) {
        log.info("callNonNull: {}", value);
    }
}
