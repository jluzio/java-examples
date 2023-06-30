package com.example.java.playground.lib.validator.jsr380;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.example.java.playground.lib.validator.jsr380.CompareToDate.List;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.temporal.Temporal;
import java.util.function.Function;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.apache.commons.collections4.functors.ComparatorPredicate.Criterion;

@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = CompareToDateValidator.class)
@Documented
@Repeatable(List.class)
public @interface CompareToDate {

  String message() default "{validation.constraints.CompareToDate.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String value();

  Criterion criterion() default Criterion.EQUAL;

  Class<? extends Function<Object, Temporal>> temporalMapper() default DefaultTemporalMapper.class;

  @Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
  @Retention(RUNTIME)
  @Documented
  @interface List {

    CompareToDate[] value();
  }
}
