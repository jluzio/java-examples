package com.example.java.playground.features.lang;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class RecordTest {

  // Note: each field is final
  public record UserRecord(String name, int age) {

  }

  @Test
  void test() {
    var user = new UserRecord("John Doe", 23);
    log.info("user: {}", user);
    log.info("user.age: {}", user.age());
    assertThat(user)
        .hasNoNullFieldsOrProperties();

    var anotherUser = new UserRecord("Jane Doe", 24);
    var sameDataUser = new UserRecord("John Doe", 23);
    assertThat(user)
        .isNotEqualTo(anotherUser)
        .isEqualTo(sameDataUser);
  }

}
