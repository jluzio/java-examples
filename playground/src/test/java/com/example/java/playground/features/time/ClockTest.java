package com.example.java.playground.features.time;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;

class ClockTest {

  @Test
  void systemDefaultZone() {
    var clock = Clock.systemDefaultZone();
    // note: can fail if the test runs at the changing of day :)
    assertThat(LocalDate.now(clock))
        .isEqualTo(LocalDate.now());
  }

  @Test
  void systemUTC() {
    var clock = Clock.systemUTC();
    // note: can fail if the test runs at the changing of day :)
    assertThat(LocalDate.now(clock))
        .isEqualTo(LocalDate.now());
  }

  /**
   * Good for testing classes that depend on current time
   */
  @Test
  void fixed() {
    LocalDate fixedLocalDate = LocalDate.of(2000, 1, 2);
    LocalTime fixedLocalTime = LocalTime.of(12, 13);
    LocalDateTime fixedLocalDateTime = fixedLocalDate.atTime(fixedLocalTime);
    OffsetDateTime fixedDateTime = fixedLocalDateTime.atOffset(ZoneOffset.UTC);

    var clock = Clock.fixed(
        fixedDateTime.toInstant(),
        ZoneOffset.UTC);

    assertThat(LocalDate.now(clock))
        .isEqualTo(fixedLocalDate);
    assertThat(LocalTime.now(clock))
        .isEqualTo(fixedLocalTime);
    assertThat(LocalDateTime.now(clock))
        .isEqualTo(fixedLocalDateTime);
    assertThat(OffsetDateTime.now(clock))
        .isEqualTo(fixedDateTime);
  }

}
