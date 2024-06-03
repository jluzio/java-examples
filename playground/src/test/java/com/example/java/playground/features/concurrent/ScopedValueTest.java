package com.example.java.playground.features.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class ScopedValueTest {

  record User(String id, String username) {

  }

  record Permissions(String role) {

  }

  public final static ScopedValue<User> USER = ScopedValue.newInstance();
  public final static ScopedValue<Permissions> PERMISSIONS = ScopedValue.newInstance();

  @Test
  void test() throws Exception {
    ScopedValue.runWhere(
        USER,
        new User("1", "user1"),
        () -> log.info("user: {}", USER.get())
    );

    ScopedValue
        .where(USER, new User("2", "user2"))
        .run(() -> log.info("user: {}", USER.get()));

    var msg = ScopedValue
        .where(USER, new User("3", "user3"))
        .call(() -> STR. "user: \{ USER.get() }" );
    log.info(msg);

    var userId = ScopedValue
        .where(USER, new User("2", "user2"))
        .get(() -> USER.get().id());
    log.info("userId: {}", userId);

    var user = ScopedValue
        .where(USER, new User("2", "user2"))
        .get(USER);
    log.info("user: {}", user);
  }

  @Test
  void test_multiple() {
    ScopedValue
        .where(USER, new User("2", "user2"))
        .where(PERMISSIONS, new Permissions("admin"))
        .run(() -> log.info("user: {} | permissions: {}", USER.get(), PERMISSIONS.get()));
  }

}
