package com.example.java.playground.lib.validator.jsr380;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ValidatorTest {

  @Autowired
  Validator validator;

  @Test
  void test() {
    var person = Person.builder()
        .id("id")
        .name("")
        .age(123)
        .build();

    var constraintViolations = validator.validate(person);
    log.info("constraintViolations: {}", constraintViolations);
    assertThat(constraintViolations)
        .isNotEmpty();
  }
}
