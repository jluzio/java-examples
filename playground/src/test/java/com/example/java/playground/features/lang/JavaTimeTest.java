package com.example.java.playground.features.lang;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class JavaTimeTest {

  @Test
  void temporal_variants() {
    var localDate = LocalDate.of(2020, 1, 1);
    log.info("localDate: {}", localDate);
    var localTime = LocalTime.of(10, 12, 13);
    log.info("localTime: {}", localTime);
    LocalDateTime localDateTime = localDate.atTime(localTime);
    log.info("localDateTime: {}", localDateTime);
    ZonedDateTime zonedDateTimeUTC = localDateTime.atZone(ZoneOffset.UTC);
    log.info("zonedDateTimeUTC: {}", zonedDateTimeUTC);
    ZonedDateTime zonedDateTimeCurrent = zonedDateTimeUTC
        .withZoneSameInstant(ZoneOffset.ofHours(5));
    log.info("zonedDateTimeCurrent: {}", zonedDateTimeCurrent);

    log.info("today with Time Max: {}", localDate.atTime(LocalTime.MAX));
  }

  @Test
  void periods() {
    var date1 = LocalDate.parse("2000-01-01");
    var date2 = LocalDate.parse("2000-02-20");
    var period = Period.between(date1, date2);
    assertThat(period)
        .isEqualTo(Period.parse("P1M19D"));
    assertThat(period.getDays())
        .isEqualTo(19);

    var yearsPeriod = Period.ofYears(7);
    assertThat(yearsPeriod.getMonths())
        .isZero();
    assertThat(yearsPeriod.toTotalMonths())
        .isEqualTo(84);
  }

  @Test
  void duration() {
    var date1 = LocalDate.parse("2000-01-01").atStartOfDay();
    var date2 = LocalDate.parse("2000-02-20").atStartOfDay();
    var duration = Duration.between(date1, date2);
    assertThat(duration)
        .isEqualTo(Duration.parse("P50D"));
    assertThat(duration.toDays())
        .isEqualTo(50);
  }

  @Test
  void chronoUnitBetween() {
    var date1 = LocalDate.parse("2000-01-01");
    var date2 = LocalDate.parse("2000-02-20");
    var days = ChronoUnit.DAYS.between(date1, date2);
    assertThat(days)
        .isEqualTo(50);
  }

}
