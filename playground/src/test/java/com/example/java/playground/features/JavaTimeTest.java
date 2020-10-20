package com.example.java.playground.features;

import com.example.java.playground.AbstractTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

public class JavaTimeTest extends AbstractTest {

  @Test
  void test_values() {
    var localDate = LocalDate.of(2020, 01, 01);
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
  }
}
