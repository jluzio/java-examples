package com.example.java.playground.lib.validator.jsr380;

import static java.time.ZoneOffset.UTC;
import static java.util.Objects.requireNonNull;

import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.function.Function;
import jakarta.validation.ConstraintDefinitionException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.functors.ComparatorPredicate;
import org.apache.commons.collections4.functors.ComparatorPredicate.Criterion;

@RequiredArgsConstructor
public class CompareToDateValidator implements ConstraintValidator<CompareToDate, Object> {

  private final ValidationSpElHelper helper;
  private Period period;
  private Criterion criterion;
  private Function<Object, Temporal> temporalMapper;


  @Override
  public void initialize(CompareToDate constraintAnnotation) {
    period = requireNonNull(helper.getValue(constraintAnnotation.value(), Period.class));
    criterion = requireNonNull(constraintAnnotation.criterion());
    try {
      var temporalMapperClass = constraintAnnotation.temporalMapper();
      if (temporalMapperClass == DefaultTemporalMapper.class) {
        temporalMapper = DefaultTemporalMapper.INSTANCE;
      } else {
        temporalMapper = constraintAnnotation.temporalMapper().getConstructor().newInstance();
      }
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
             NoSuchMethodException e) {
      throw new ConstraintDefinitionException("Unable to get temporalMapper", e);
    }
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }
    Instant targetInstant = LocalDate.now(context.getClockProvider().getClock())
        .plus(period)
        .atStartOfDay(UTC)
        .toInstant();
    Temporal valueAsTemporal = temporalMapper.apply(value);
    Instant valueAsInstant = toInstant(valueAsTemporal);
    var predicate = ComparatorPredicate.comparatorPredicate(
        valueAsInstant, Instant::compareTo, criterion);
    return predicate.evaluate(targetInstant);
  }

  private Instant toInstant(Temporal temporal) {
    if (temporal instanceof Instant) {
      return (Instant) temporal;
    } else if (temporal instanceof LocalDate) {
      return ((LocalDate) temporal).atStartOfDay().toInstant(UTC);
    } else if (temporal instanceof LocalDateTime) {
      return ((LocalDateTime) temporal).toInstant(UTC);
    } else if (temporal instanceof OffsetDateTime) {
      return ((OffsetDateTime) temporal).toInstant();
    } else if (temporal instanceof ZonedDateTime) {
      return ((ZonedDateTime) temporal).toInstant();
    } else {
      throw new IllegalArgumentException("Unsupported class: " + temporal.getClass().getName());
    }
  }

}
