package com.example.java.playground.features.time;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

class TimeTest {

  @Test
  void datetime_years() {
    var datetime1 = ZonedDateTime.parse("2022-12-31T01:02:03Z");
    assertThat(DateTimeFormatter.ofPattern("yyyy").format(datetime1))
        .isEqualTo("2022");
    assertThat(DateTimeFormatter.ofPattern("uuuu").format(datetime1))
        .isEqualTo("2022");
    assertThat(DateTimeFormatter.ofPattern("YYYY").format(datetime1))
        .isEqualTo("2022");

    var datetime2 = ZonedDateTime.parse("-0003-12-31T01:02:03Z");
    assertThat(DateTimeFormatter.ofPattern("yyyy").format(datetime2))
        .isEqualTo("0004");
    assertThat(DateTimeFormatter.ofPattern("uuuu").format(datetime2))
        .isEqualTo("-0003");
    assertThat(DateTimeFormatter.ofPattern("YYYY").format(datetime2))
        .isEqualTo("-0003");
  }

}
