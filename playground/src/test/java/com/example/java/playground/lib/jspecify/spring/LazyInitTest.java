package com.example.java.playground.lib.jspecify.spring;

import static org.assertj.core.api.Assertions.assertThat;

import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootTest
class LazyInitTest {

  @Configuration
  @Import({LazyInitBean.class})
  static class Config {

  }

  @NullMarked
  static class LazyInitBean implements InitializingBean {

    @SuppressWarnings("NullAway.Init")
    private String value;

    @Override
    public void afterPropertiesSet() throws Exception {
      value = "non-null-value";
    }

    public String getValue() {
      return value;
    }
  }

  @Autowired
  LazyInitBean lazyInitBean;

  @Test
  void test() {
    assertThat(lazyInitBean.getValue())
        .isNotEmpty();
    IO.println(lazyInitBean.getValue().length());
  }

}
