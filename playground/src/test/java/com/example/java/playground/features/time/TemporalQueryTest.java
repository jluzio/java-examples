package com.example.java.playground.features.time;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalQueries;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class TemporalQueryTest {

  @Test
  void test_allData_localDate() {
    Temporal temporal = LocalDate.parse("2000-01-02");
    var localDatePart = temporal.query(TemporalQueries.localDate());
    log.info("localDatePart: {}", localDatePart);
    var localTimePart = temporal.query(TemporalQueries.localTime());
    log.info("localTimePart: {}", localTimePart);
    var zoneOffset = temporal.query(TemporalQueries.offset());
    log.info("zoneOffset: {}", zoneOffset);
    var zoneId = temporal.query(TemporalQueries.zoneId());
    log.info("zoneId: {}", zoneId);

    assertThat(localDatePart).isNotNull();
    assertThat(localTimePart).isNull();
    assertThat(zoneOffset).isNull();
    assertThat(zoneId).isNull();
  }

  @Test
  void test_allData_localDateTime() {
    Temporal temporal = LocalDateTime.parse("2000-01-02T01:20:30");
    var localDatePart = temporal.query(TemporalQueries.localDate());
    log.info("localDatePart: {}", localDatePart);
    var localTimePart = temporal.query(TemporalQueries.localTime());
    log.info("localTimePart: {}", localTimePart);
    var zoneOffset = temporal.query(TemporalQueries.offset());
    log.info("zoneOffset: {}", zoneOffset);
    var zoneId = temporal.query(TemporalQueries.zoneId());
    log.info("zoneId: {}", zoneId);

    assertThat(localDatePart).isNotNull();
    assertThat(localTimePart).isNotNull();
    assertThat(zoneOffset).isNull();
    assertThat(zoneId).isNull();
  }

  @Test
  void test_allData_offsetDateTime() {
    Temporal temporal = OffsetDateTime.parse("2000-01-02T01:20:30+05:00");
    var localDatePart = temporal.query(TemporalQueries.localDate());
    log.info("localDatePart: {}", localDatePart);
    var localTimePart = temporal.query(TemporalQueries.localTime());
    log.info("localTimePart: {}", localTimePart);
    var zoneOffset = temporal.query(TemporalQueries.offset());
    log.info("zoneOffset: {}", zoneOffset);
    var zoneId = temporal.query(TemporalQueries.zoneId());
    log.info("zoneId: {}", zoneId);

    assertThat(localDatePart).isNotNull();
    assertThat(localTimePart).isNotNull();
    assertThat(zoneOffset).isNotNull();
    assertThat(zoneId).isNull();

    var offsetDateTime = OffsetDateTime.of(localDatePart, localTimePart, zoneOffset);
    assertThat(offsetDateTime)
        .isEqualTo(temporal);
  }

}
