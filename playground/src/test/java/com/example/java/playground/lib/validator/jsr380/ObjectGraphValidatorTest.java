package com.example.java.playground.lib.validator.jsr380;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Valid;
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
class ObjectGraphValidatorTest {

  @Autowired
  Validator validator;

  record Car(@Valid @NotEmpty List<Part> parts) {
  }

  record Part(@NotEmpty String name) {

  }

  @Test
  void test() {
    var car = new Car(List.of(new Part("")));
    var constraintViolations = validator.validate(car);
    log.info("constraintViolations: {}", constraintViolations);
    assertThat(constraintViolations)
        .isNotEmpty();
  }

}
