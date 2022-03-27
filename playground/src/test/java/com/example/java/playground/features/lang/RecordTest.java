package com.example.java.playground.features.lang;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class RecordTest {

  // Note: each field is final
  public record UserRecord(String name, int age) {

  }

  @Test
  public void test() {
    var user = new UserRecord("John Doe", 23);
    log.info("user: {}", user);
    log.info("user.age: {}", user.age());
  }

}
