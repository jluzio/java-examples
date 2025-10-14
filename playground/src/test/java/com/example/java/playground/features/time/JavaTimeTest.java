package com.example.java.playground.features.time;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
  void datetime_formats() {
    // year variants - 1
    var datetime_year1 = ZonedDateTime.parse("2022-12-31T01:02:03Z");
    assertThat(DateTimeFormatter.ofPattern("yyyy").format(datetime_year1))
        .isEqualTo("2022");
    assertThat(DateTimeFormatter.ofPattern("uuuu").format(datetime_year1))
        .isEqualTo("2022");
    assertThat(DateTimeFormatter.ofPattern("YYYY").format(datetime_year1))
        .isEqualTo("2022");

    // year variants - 2
    var datetime_year2 = ZonedDateTime.parse("-0003-06-01T01:02:03Z");
    assertThat(DateTimeFormatter.ofPattern("yyyy").format(datetime_year2))
        .isEqualTo("0004");
    assertThat(DateTimeFormatter.ofPattern("uuuu").format(datetime_year2))
        .isEqualTo("-0003");
    assertThat(DateTimeFormatter.ofPattern("YYYY").format(datetime_year2))
        .isEqualTo("-0003");

    // zone variants - 1
    var datetime_zone1 = ZonedDateTime.parse("2000-01-02T01:02:03Z");
    assertThat(DateTimeFormatter.ofPattern("yyyy-MM-ddZ").format(datetime_zone1))
        .isEqualTo("2000-01-02+0000");
    assertThat(DateTimeFormatter.ofPattern("yyyy-MM-ddX").format(datetime_zone1))
        .isEqualTo("2000-01-02Z");
    assertThat(DateTimeFormatter.ofPattern("yyyy-MM-ddx").format(datetime_zone1))
        .isEqualTo("2000-01-02+00");

    // zone variants - 2
    var datetime_zone2 = OffsetDateTime.parse("2000-01-02T01:02:03+01:00");
    assertThat(DateTimeFormatter.ofPattern("yyyy-MM-ddZ").format(datetime_zone2))
        .isEqualTo("2000-01-02+0100");
    assertThat(DateTimeFormatter.ofPattern("yyyy-MM-ddX").format(datetime_zone2))
        .isEqualTo("2000-01-02+01");
    assertThat(DateTimeFormatter.ofPattern("yyyy-MM-ddx").format(datetime_zone2))
        .isEqualTo("2000-01-02+01");

    // zone variants - 3
    var datetime_zone3 = OffsetDateTime.parse("2000-01-02T01:02:03+01:30");
    assertThat(DateTimeFormatter.ofPattern("yyyy-MM-ddZ").format(datetime_zone3))
        .isEqualTo("2000-01-02+0130");
    assertThat(DateTimeFormatter.ofPattern("yyyy-MM-ddX").format(datetime_zone3))
        .isEqualTo("2000-01-02+0130");
    assertThat(DateTimeFormatter.ofPattern("yyyy-MM-ddx").format(datetime_zone3))
        .isEqualTo("2000-01-02+0130");
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
