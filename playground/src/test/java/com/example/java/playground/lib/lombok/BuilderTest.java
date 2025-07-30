package com.example.java.playground.lib.lombok;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class BuilderTest {

  @Data
  @SuperBuilder
  public static class Parent {

    private String parent1;
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  @ToString(callSuper = true)
  @SuperBuilder
  public static class Child extends Parent {

    private String child1;
  }

  @Builder
  record SomeRecordBuilderDefaults(String value1, String value2) {

    @SuppressWarnings("unused")
    public static class SomeRecordBuilderDefaultsBuilder {

      private String value1 = "default-value1";
    }
  }

  @Builder
  record SomeRecordConstructorDefaults(String value1, String value2) {

    SomeRecordConstructorDefaults {
      if (value1 == null) {
        value1 = "default-value1";
      }
    }

    @SuppressWarnings("unused")
    public static class SomeRecordBuilderDefaultsBuilder {

      private String value1 = "default-value1";
    }
  }

  @Test
  void superBuilder() {
    Child child = Child.builder()
        .parent1("val1")
        .child1("val2")
        .build();
    log.info("child: {}", child);
  }

  @Test
  void recordBuilderDefaults_builderDefaults() {
    var allValues = SomeRecordBuilderDefaults.builder()
        .value1("value1")
        .value2("value2")
        .build();
    assertThat(allValues.value1())
        .isEqualTo("value1");
    assertThat(allValues.value2())
        .isEqualTo("value2");

    var usingDefaultValues = SomeRecordBuilderDefaults.builder()
        .build();
    assertThat(usingDefaultValues.value1())
        .isEqualTo("default-value1");
    assertThat(usingDefaultValues.value2())
        .isNull();
  }

  @Test
  void recordBuilderDefaults_constructorDefaults() {
    var allValues = SomeRecordConstructorDefaults.builder()
        .value1("value1")
        .value2("value2")
        .build();
    assertThat(allValues.value1())
        .isEqualTo("value1");
    assertThat(allValues.value2())
        .isEqualTo("value2");

    var usingDefaultValues = SomeRecordConstructorDefaults.builder()
        .build();
    assertThat(usingDefaultValues.value1())
        .isEqualTo("default-value1");
    assertThat(usingDefaultValues.value2())
        .isNull();
  }

}
