package com.example.java.playground.lib.vavr;

import static org.assertj.core.api.Assertions.assertThat;

import io.vavr.control.Option;
import io.vavr.control.Try;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

class OptionTest {

  @Test
  void option_default() {
    Integer value = Option.of("")
        .flatMap(v -> Option.of(StringUtils.trimToNull(v)))
        .orElse(Option.of("default"))
        .map(String::length)
        .getOrElse(0);

    assertThat(value)
        .isEqualTo(7);
  }

  record Data(String id, String value) {

  }

  @Test
  void withTry() {
    ObjectMapper mapper = new ObjectMapper();
    Try<Data> data = Option.of("{\"id\":\"id1\"}")
        .toTry()
        .mapTry(v -> mapper.readValue(v, Data.class));
    Assertions.<Try<Data>>assertThat(data)
        .matches(Try::isSuccess)
        .extracting(Try::get)
        .isEqualTo(new Data("id1", null));
  }

}
