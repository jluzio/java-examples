package com.example.java.playground.lib.streamex;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.EntryStream;
import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;
import org.junit.jupiter.api.Test;

@Slf4j
class StreamExTest {

  record User(String id, String name, Role role) {

  }

  record Role(String id) {

  }
  //@formatter:on

  public static final Role ROLE_USER = new Role("user");
  public static final Role ROLE_ADMIN = new Role("admin");

  private final User user1 = new User("id1", "user1", ROLE_ADMIN);
  private final User user2 = new User("id2", "user2", ROLE_USER);
  private final User user3 = new User("id3", "user3", ROLE_USER);
  private final List<User> users = List.of(user1, user2, user3);

  @Test
  void map_concat() {
    var map1 = Map.of("1", "v1");
    var map2 = Map.of("2", "v2");
    var expected = Map.of(
        "1", "v1",
        "2", "v2");

    var map3 = EntryStream.of(map1)
        .append(EntryStream.of(map2))
        .toMap();
    assertThat(map3).isEqualTo(expected);
  }

  @Test
  void collectors() {
    assertThat(
        StreamEx.of(users)
            .map(User::name)
            .toList())
        .isEqualTo(List.of("user1", "user2", "user3"));

    assertThat(
        StreamEx.of(users)
            .groupingBy(User::role))
        .isEqualTo(Map.of(
            ROLE_USER, List.of(user2, user3),
            ROLE_ADMIN, List.of(user1)
        ));

    assertThat(
        StreamEx.of(1, 2, 3)
            .joining("; "))
        .isEqualTo("1; 2; 3");
  }

  @Test
  void filters() {
    assertThat(
        StreamEx.of("string", 42)
            .select(String.class)
            .toList())
        .isEqualTo(List.of("string"));
  }

  @Test
  void append() {
    assertThat(
        StreamEx.of(users)
            .map(User::name)
            .prepend("(none)")
            .append("LAST")
            .toList())
        .isEqualTo(List.of("(none)", "user1", "user2", "user3", "LAST"));
  }

  @Test
  void pairMap() {
    IntSupplier intSupplier = new IntSupplier() {
      int value = 1;

      @Override
      public int getAsInt() {
        value *= 2;
        return value;
      }
    };
    var numbers = IntStream.generate(intSupplier)
        .limit(5)
        .toArray();
    assertThat(IntStreamEx.of(numbers)
        .pairMap((a, b) -> b - a)
        .toArray())
        .isEqualTo(IntStream.of(2, 4, 8, 16).toArray());
  }

  @Test
  void map_functions() {
    Map<String, Role> nameToRole = new HashMap<>();
    nameToRole.put("first", ROLE_USER);
    nameToRole.put("second", null);
    assertThat(
        StreamEx.ofKeys(nameToRole, Objects::nonNull)
            .toSet())
        .isEqualTo(Set.of("first"));
  }

  @Test
  void map_transform_entries() {
    Map<Role, List<User>> role2users = Map.of(
        ROLE_USER, List.of(user2, user3),
        ROLE_ADMIN, List.of(user1));
    Map<User, List<Role>> users2roles = EntryStream.of(role2users)
        .flatMapValues(List::stream)
        .invert()
        .grouping();
    log.debug("users2roles: {}", users2roles);
    assertThat(users2roles)
        .isEqualTo(Map.of(
            user1, List.of(user1.role()),
            user2, List.of(user2.role()),
            user3, List.of(user3.role())
        ));

    assertThat(
        EntryStream.of(users2roles)
            .mapKeys(String::valueOf)
            .mapValues(String::valueOf)
            .toMap())
        .isEqualTo(Map.of(
            user1.toString(), List.of(user1.role()).toString(),
            user2.toString(), List.of(user2.role()).toString(),
            user3.toString(), List.of(user3.role()).toString()
        ));
  }

  @Test
  void io() {
    var text = """
        Line 1 text
                
        Line 2 text
                
        Line 3 text
        """;
    try (var reader = new StringReader(text)) {
      assertThat(
          StreamEx.ofLines(reader)
              .remove(String::isEmpty)
              .toList())
          .isEqualTo(List.of("Line 1 text", "Line 2 text", "Line 3 text"));
    }
  }

  @Test
  void factories() {
    assertThat(StreamEx.ofReversed(List.of(1, 2, 3)).toList())
        .isEqualTo(List.of(3, 2, 1));
  }

}
