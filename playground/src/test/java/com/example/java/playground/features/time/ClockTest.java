package com.example.java.playground.features.time;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

class ClockTest {

  record Message(String message, OffsetDateTime timestamp) {

  }

  @RequiredArgsConstructor
  class MessageService {
    private final Clock clock;

    public Message createMessage(String message) {
      return new Message(message, OffsetDateTime.now(clock));
    }

  }

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

  @Test
  void offset() {
    OffsetDateTime baseFixedDateTime = LocalDate.of(2000, 1, 2)
        .atTime(LocalTime.of(12, 13))
        .atOffset(ZoneOffset.UTC);
    OffsetDateTime fixedDateTime = baseFixedDateTime.plusMinutes(3);
    assertThat(fixedDateTime)
        .isEqualTo(LocalDate.of(2000, 1, 2)
            .atTime(LocalTime.of(12, 16))
            .atOffset(ZoneOffset.UTC));

    var baseClock = Clock.fixed(
        baseFixedDateTime.toInstant(),
        ZoneOffset.UTC);
    var clock = Clock.offset(baseClock, Duration.ofMinutes(3));

    assertThat(OffsetDateTime.now(baseClock))
        .isEqualTo(baseFixedDateTime);
    assertThat(OffsetDateTime.now(clock))
        .isEqualTo(fixedDateTime);
  }

  @Test
  void tick() {
    OffsetDateTime baseFixedDateTime = LocalDate.of(2000, 1, 2)
        .atTime(LocalTime.of(12, 13, 33))
        .atOffset(ZoneOffset.UTC);

    var baseClock = Clock.fixed(
        baseFixedDateTime.toInstant(),
        ZoneOffset.UTC);
    var clock = Clock.tick(baseClock, Duration.ofMinutes(1));

    assertThat(OffsetDateTime.now(baseClock))
        .isEqualTo(baseFixedDateTime);
    // ticks are 1 minute, so 33 seconds isn't a full tick, which will be rounded to 0
    assertThat(OffsetDateTime.now(clock))
        .isEqualTo(baseFixedDateTime.withSecond(0));
  }

  /**
   * Good for testing classes that depend on current time
   */
  @Test
  void fixedAsDependency() {
    OffsetDateTime fixedDateTime = LocalDate.of(2000, 1, 2)
        .atTime(LocalTime.of(12, 13))
        .atOffset(ZoneOffset.UTC);
    var clock = Clock.fixed(fixedDateTime.toInstant(), ZoneOffset.UTC);

    var messageService = new MessageService(clock);
    assertThat(messageService.createMessage("Hello clock!"))
        .isEqualTo(new Message("Hello clock!", fixedDateTime));
  }

}
