package com.example.java.playground.lib.validator.jsr380;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validator;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ValidationAutoConfiguration.class)
@Slf4j
class ContainerElementValidatorTest {

  @Autowired
  Validator validator;

  record Car(List<@NotEmpty String> parts) {
  }

  @Test
  void list() {
    var car = new Car(List.of(""));
    var constraintViolations = validator.validate(car);
    log.info("constraintViolations: {}", constraintViolations);
    assertThat(constraintViolations)
        .isNotEmpty();
  }

}
