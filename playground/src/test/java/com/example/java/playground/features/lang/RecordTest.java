package com.example.java.playground.features.lang;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class RecordTest {

  // Note: each field is final
  @Builder
  public record UserRecord(String name, int age) {

    public UserRecord {
      if (name == null) {
        name = "unknown";
      }
      if (age < 0) {
        throw new IllegalArgumentException("age");
      }
    }
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

    assertThatThrownBy(() -> new UserRecord("John Doe", -1))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void test_defaults() {
    assertThat(new UserRecord(null, 23))
        .isEqualTo(new UserRecord("unknown", 23));
    assertThat(UserRecord.builder().age(23).build())
        .isEqualTo(new UserRecord("unknown", 23));
    assertThat(UserRecord.builder().name("some_name").age(23).build())
        .isEqualTo(new UserRecord("some_name", 23));
  }

}
