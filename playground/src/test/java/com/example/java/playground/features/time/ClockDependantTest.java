package com.example.java.playground.features.time;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootTest
class ClockDependantTest {

  @Configuration
  @Import(ClockDependantBean.class)
  static class Config {

    @Bean
    Clock clock() {
      return Clock.fixed(FIXED_INSTANT, ZoneOffset.UTC);
    }
  }

  public static final Instant FIXED_INSTANT = Instant.parse("2000-01-02T03:04:05Z");
  @Autowired
  ClockDependantBean bean;

  @Test
  void test() {
    assertThat(bean.sayTime())
        .isEqualTo(LocalDateTime.ofInstant(FIXED_INSTANT, ZoneOffset.UTC));
  }

  @RequiredArgsConstructor
  static class ClockDependantBean {

    private final Clock clock;

    public LocalDateTime sayTime() {
      return LocalDateTime.now(clock);
    }
  }
}
