package com.example.java.playground.lib.validator.jsr380;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {ValidationAutoConfiguration.class})
@Slf4j
class ValidatorXmlConfigTest {

  @Autowired
  Validator validator;

  @Test
  void test() {
    var person = new ClosedClassPerson();
    log.debug("{} :: {}", person.getClass().getName(), person);
    var constraintViolations = validator.validate(person);
    log.info("constraintViolations: {}", constraintViolations);
    assertThat(constraintViolations).isNotEmpty();
  }


  @Data
  @RequiredArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class ClosedClassPerson {

    private String id;
    private String name;
    private Integer age;
    private String email;

  }
}
