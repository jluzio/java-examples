package com.example.java.playground.features;

import com.example.java.playground.AbstractTest;
import org.junit.jupiter.api.Test;

public class RecordTest extends AbstractTest {

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
