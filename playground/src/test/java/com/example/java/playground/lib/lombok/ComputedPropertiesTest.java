package com.example.java.playground.lib.lombok;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

class ComputedPropertiesTest {

  @Data
  @RequiredArgsConstructor
  @AllArgsConstructor
  @Builder
  static class Bean {

    private String rawCsvValue;
    private List<String> csvValue;

    public void setRawCsvValue(String rawCsvValue) {
      this.rawCsvValue = rawCsvValue;
      this.csvValue = Arrays.asList(rawCsvValue.split(","));
    }

    static class BeanBuilder {

      public BeanBuilder rawCsvValue(String rawCsvValue) {
        this.rawCsvValue = rawCsvValue;
        this.csvValue = Arrays.asList(rawCsvValue.split(","));
        return this;
      }
    }
  }

  @Test
  void test() {
    var bean = new Bean();
    bean.setRawCsvValue("1,2,3");
    assertThat(bean.getCsvValue())
        .contains("1", "2", "3");

    var beanByBuilder = Bean.builder()
        .rawCsvValue("1,2,3")
        .build();
    assertThat(beanByBuilder.getCsvValue())
        .contains("1", "2", "3");
  }

}
