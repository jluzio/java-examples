package com.example.java.playground.lombok;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

class ConstructorsTest {

  @Data
  @Builder
  @RequiredArgsConstructor
//  @AllArgsConstructor
  static class NoParamsClass {

  }

  @Data
  @Builder
  @RequiredArgsConstructor
  @AllArgsConstructor
  static class ParamsClass {

    private String value1;
    private String value2;
  }

  @Data
  @Builder
  @RequiredArgsConstructor
  @AllArgsConstructor
  static class RequiredParamsClass {

    private final String value1;
    private String value2;
  }

  @Test
  void test() {
    var noParams1 = new NoParamsClass();
    var noParams2 = NoParamsClass.builder().build();
    assertThat(noParams1).isEqualTo(noParams2);

    var params1 = new ParamsClass();
    var params2 = ParamsClass.builder().build();
    assertThat(params1).isEqualTo(params2);

    var reqParams1 = new RequiredParamsClass("value1");
    var reqParams2 = RequiredParamsClass.builder()
        .value1("value1")
        .build();
    assertThat(reqParams1).isEqualTo(reqParams2);
  }

}
