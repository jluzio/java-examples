package com.example.java.playground.lib.validator.jsr380;

import static java.time.ZoneOffset.UTC;
import static org.apache.commons.collections4.functors.ComparatorPredicate.Criterion.GREATER_OR_EQUAL;
import static org.apache.commons.collections4.functors.ComparatorPredicate.Criterion.LESS;
import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Future;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Slf4j
class CompareToDateValidatorTest {

  @Configuration
  @Import({ValidationAutoConfiguration.class, ValidationSpElHelper.class})
  @EnableConfigurationProperties
  static class Config {

    @Bean
    public Period someConfigPeriod() {
      return Period.parse("P1M");
    }

  }

  public static final String DEFAULT_MESSAGE = "invalid date regarding current date and given period";

  public static class DateStringTemporalMapper implements Function<Object, Temporal> {

    @Override
    public Instant apply(Object object) {
      if (object == null) {
        return null;
      }
      String value = (String) object;
      var localDateTime = LocalDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME);
      return localDateTime.toInstant(UTC);
    }
  }

  @Value
  @AllArgsConstructor
  @Builder
  @Valid
  static class ValidatedBean {

    @CompareToDate(value = "#{T(java.time.Period).parse('P2D')}", criterion = GREATER_OR_EQUAL)
    LocalDate localDate1;
    @Future
    @CompareToDate(value = "#{someConfigPeriod}", criterion = GREATER_OR_EQUAL)
    LocalDate localDate2;
    @CompareToDate(value = "#{T(java.time.Period).parse('P2D')}", criterion = LESS)
    LocalDate localDate3;
    @CompareToDate(value = "#{T(java.time.Period).parse('P2D')}", criterion = GREATER_OR_EQUAL)
    Instant instant1;
    @CompareToDate(value = "#{T(java.time.Period).parse('P2D')}", criterion = GREATER_OR_EQUAL, temporalMapper = DateStringTemporalMapper.class)
    String dateString1;

  }

  @Autowired
  Validator validator;

  @Test
  void validation_localDate_success() {
    var validatedBean = ValidatedBean.builder()
        .localDate1(LocalDate.now().plusDays(2))
        .localDate2(LocalDate.now().plusYears(1))
        .localDate3(LocalDate.now().plusDays(1))
        .build();
    var constraintViolations = validator.validate(validatedBean);
    log.debug("constraintViolations: {}", constraintViolations);
    assertThat(constraintViolations)
        .isEmpty();
  }

  @Test
  void validation_localDate_error_1() {
    var validatedBean = ValidatedBean.builder()
        .localDate1(LocalDate.now(UTC).plusDays(1))
        .localDate2(LocalDate.now(UTC).plusDays(1))
        .localDate2(LocalDate.now(UTC).plusDays(2))
        .build();
    var constraintViolations = validator.validate(validatedBean);
    log.debug("constraintViolations: {}", constraintViolations);
    assertThat(constraintViolations)
        .isNotEmpty()
        .extracting(ConstraintViolation::getMessage)
        .allMatch(DEFAULT_MESSAGE::equals);
  }

  @Test
  void validation_localDate_error_2() {
    var validatedBean = ValidatedBean.builder()
        .localDate1(LocalDate.now().plusDays(2))
        .localDate2(LocalDate.now())
        .build();
    var constraintViolations = validator.validate(validatedBean);
    log.debug("constraintViolations: {}", constraintViolations);
    assertThat(constraintViolations)
        .isNotEmpty()
        .extracting(ConstraintViolation::getMessage)
        .contains(DEFAULT_MESSAGE, "must be a future date");
  }

  @Test
  void validation_instant_success() {
    var validatedBean = ValidatedBean.builder()
        .instant1(Instant.now().plus(2, ChronoUnit.DAYS))
        .build();
    var constraintViolations = validator.validate(validatedBean);
    log.debug("constraintViolations: {}", constraintViolations);
    assertThat(constraintViolations)
        .isEmpty();
  }

  @Test
  void validation_instant_error_1() {
    var validatedBean = ValidatedBean.builder()
        .instant1(Instant.now().plus(1, ChronoUnit.DAYS))
        .build();
    var constraintViolations = validator.validate(validatedBean);
    log.debug("constraintViolations: {}", constraintViolations);
    assertThat(constraintViolations)
        .isNotEmpty()
        .extracting(ConstraintViolation::getMessage)
        .allMatch(DEFAULT_MESSAGE::equals);
  }

  @Test
  void validation_temporalMapper_error() {
    var validatedBean = ValidatedBean.builder()
        .dateString1(OffsetDateTime.now(UTC)
            .format(DateTimeFormatter.ISO_DATE_TIME))
        .build();
    var constraintViolations = validator.validate(validatedBean);
    log.debug("constraintViolations: {}", constraintViolations);
    assertThat(constraintViolations)
        .isNotEmpty()
        .extracting(ConstraintViolation::getMessage)
        .contains(DEFAULT_MESSAGE);
  }

  @Test
  void validation_temporalMapper_success() {
    var validatedBean = ValidatedBean.builder()
        .dateString1(
            OffsetDateTime.now(UTC)
                .plusDays(2)
                .format(DateTimeFormatter.ISO_DATE_TIME))
        .build();
    var constraintViolations = validator.validate(validatedBean);
    log.debug("constraintViolations: {}", constraintViolations);
    assertThat(constraintViolations)
        .isEmpty();
  }

}