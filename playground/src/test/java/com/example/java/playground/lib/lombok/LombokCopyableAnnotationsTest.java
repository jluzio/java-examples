package com.example.java.playground.lib.lombok;

import com.example.java.playground.lib.lombok.LombokCopyableAnnotationsTest.Config.DataBean;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@SpringBootTest
@Slf4j
public class LombokCopyableAnnotationsTest {

  @Autowired
  DataBean dataBean;

  @Test
  public void test() {
    log.info("data: {}", dataBean);
  }

  @Configuration
  static class Config {

    @Bean("data-v1")
    String data_v1() {
      return "data-v1";
    }

    @Bean("data-v2")
    String data_v2() {
      return "data-v2";
    }

    @RequiredArgsConstructor
    @ToString
    @Component
    public static class DataBean {

      @Qualifier("data-v1")
      private final String data;

    }

  }


}
