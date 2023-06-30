package com.example.java.playground.lib.validator.jsr380;

import static java.lang.System.lineSeparator;
import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootTest
@Slf4j
class ValidationSpElHelperTest {

  @Configuration
  static class Config {

    @Bean
    ValidationSpElHelper validationSpElHelper(ConfigurableBeanFactory beanFactory) {
      return new ValidationSpElHelper(beanFactory);
    }

    @Bean
    Integer someBean() {
      return 42;
    }

  }

  @Autowired
  ValidationSpElHelper helper;
  @Autowired
  ApplicationContext beanFactory;

  @Test
  void beans() {
    var lineSep = lineSeparator();
    log.debug("--- Beans ---{}{}{}--- Beans (end) ---",
        lineSep,
        String.join(lineSeparator(), beanFactory.getBeanDefinitionNames()),
        lineSep);
  }

  @Test
  void getValue() {
    String value = helper.getValue("#{someBean}", String.class);
    log.debug("{}", value);
    assertThat(value).isEqualTo("42");
  }
}