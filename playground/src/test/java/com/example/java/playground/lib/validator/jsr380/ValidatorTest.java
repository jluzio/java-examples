package com.example.java.playground.lib.validator.jsr380;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ValidationAutoConfiguration.class)
@Slf4j
class ValidatorTest {

  public static final Person CREATE_VALID_PERSON = Person.builder()
      .id("id")
      .name("required-name")
      .age(42)
      .build();
  public static final Person UPDATE_VALID_PERSON = CREATE_VALID_PERSON.toBuilder()
      .email("email@server.org")
      .build();
  public static final Person INVALID_PERSON = Person.builder()
      .id("id")
      .name("")
      .age(123)
      .build();
  @Autowired
  Validator validator;

  @Test
  void test_default() {
    var createValidConstraintViolations = validator.validate(
        CREATE_VALID_PERSON);
    log.info("createValidConstraintViolations: {}", createValidConstraintViolations);
    assertThat(createValidConstraintViolations).isEmpty();

    var invalidConstraintViolations = validator.validate(INVALID_PERSON);
    log.info("invalidConstraintViolations: {}", invalidConstraintViolations);
    assertThat(invalidConstraintViolations).isNotEmpty();
  }

  @Test
  void test_groups() {
    var createValidConstraintViolations = validator.validate(
        CREATE_VALID_PERSON);
    log.info("createValidConstraintViolations: {}", createValidConstraintViolations);
    assertThat(createValidConstraintViolations).isEmpty();

    var updateInvalidConstraintViolations1 = validator.validate(
        CREATE_VALID_PERSON, ValidationGroups.OnUpdate.class);
    log.info("updateInvalidConstraintViolations1: {}", updateInvalidConstraintViolations1);
    assertThat(updateInvalidConstraintViolations1).isNotEmpty();

    var updateInvalidConstraintViolations2 = validator.validate(
        INVALID_PERSON.toBuilder().email("email@server.org").build(),
        ValidationGroups.OnUpdate.class);
    log.info("updateInvalidConstraintViolations2: {}", updateInvalidConstraintViolations2);
    assertThat(updateInvalidConstraintViolations2).isNotEmpty();

    var updateValidConstraintViolations = validator.validate(
        UPDATE_VALID_PERSON, ValidationGroups.OnUpdate.class);
    log.info("updateValidConstraintViolations: {}", updateValidConstraintViolations);
    assertThat(updateValidConstraintViolations).isEmpty();
  }
}
