package com.example.java.playground.lib.testdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class JavaFakerTest {

  FakeValuesService fakeValuesService = new FakeValuesService(Locale.ENGLISH, new RandomService());
  Faker faker = new Faker();
  Faker fakerWithParams = new Faker(Locale.ENGLISH, new RandomService());

  @Test
  void fakeValuesService() {
    String email = fakeValuesService.bothify("????##@gmail.com");
    log.info("{}", email);
    assertThat(email)
        .matches("\\w{4}\\d{2}@gmail.com");

    String regexp = fakeValuesService.regexify("[a-z1-9]{10}");
    log.info("{}", regexp);
    assertThat(regexp)
        .matches("[a-z1-9]{10}");
  }

  @Test
  void faker_name() {
    String name = "Hello %s %s".formatted(faker.name().firstName(), faker.name().lastName());
    log.info(name);
    assertThat(name)
        .matches("Hello [\\w']+ [\\w']+");
  }

  @Test
  void faker_address() {
    log.info("country: {}", faker.address().country());
    log.info("city: {}", faker.address().city());
  }

  // maybe test others...

}
