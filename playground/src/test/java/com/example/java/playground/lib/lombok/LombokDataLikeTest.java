package com.example.java.playground.lib.lombok;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Value;
import lombok.With;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
class LombokDataLikeTest {

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

  @Builder(toBuilder = true)
  @Value
  // Note: needed to be static inside another class
  public static class BuilderBean {

    String value1;
    String value2;
  }

  @Test
  void test_builder() {
    var data = BuilderBean.builder()
        .value1("123")
        .value2("234")
        .build();
    log.info("builder: {}", data);

    // using toBuilder
    var data2 = data.toBuilder().value1("something else").build();
    log.info("builder1: {}, builder2: {}", data, data2);
  }

  @With
  @Value
  class WithBean {

    String value1;
    String value2;
  }

  @Test
  void test_with() {
    val data = new WithBean("value1", "value2");
    val data2 = data.withValue1("anotherValue1");
    log.info("data: {}, data2: {}", data, data2);
  }
}
