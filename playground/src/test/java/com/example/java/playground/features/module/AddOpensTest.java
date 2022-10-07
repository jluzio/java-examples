package com.example.java.playground.features.module;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.jupiter.api.Test;

@Slf4j
class AddOpensTest {

  record Data(String id, String name) {

  }

  @Test
  void test() {
    var data = new Data("id1", "name1");
    var target = List.of(data);
    log.debug("{}", ReflectionToStringBuilder.toString(target));
  }
}
