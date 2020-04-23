package com.example.java.playground.lombok;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class LombokDataLikeTest {
    @Data
    class DataBean {
        @NonNull
        private String value;
    }

    @Test
    void test_data() {
        var data = new DataBean("test");
        log.info("data: {}", data);

        // non null checks
        try {
            data.setValue(null);
            Assertions.fail("should throw exception");
        } catch (NullPointerException e) {
            // expected
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @ToString
    @NoArgsConstructor
    @RequiredArgsConstructor
    @AllArgsConstructor
    class DetailedDataBean {
        @NonNull
        private String value1;
        private String value2;
    }

    @Value
    class ValueBean {
        // value field becomes private
        String value;
    }

    @Test
    void test_value() {
        var value = new ValueBean("val-1");
        // no setters
        log.info("value: {}", value);
    }

    @Builder
    @Value
    // Note: needed to be static inside another class
    public static class BuilderBean {
        private String value1;
        private String value2;
    }
    @Test
    void test_builder() {
        var data = BuilderBean.builder()
                .value1("123")
                .value2("234")
                .build();
        log.info("builder: {}", data);
    }

    @With
    @Value
    class WithBean {
        private String value1;
        private String value2;
    }

    @Test
    void test_with() {
        val data = new WithBean("value1", "value2");
        val data2 = data.withValue1("anotherValue1");
        log.info("data: {}, data2: {}", data, data2);
    }
}
