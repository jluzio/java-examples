package com.example.java.playground.lib.reactive.reactor;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import one.util.streamex.StreamEx;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

class CollectionTest {

  enum Role {USER, ADMIN}

  record User(String id, String username, String fullName, Role role) {

  }

  @Test
  void map_concat() {
    var map1 = Map.of("1", "v1");
    var map2 = Map.of("2", "v2");
    var expected = Map.of(
        "1", "v1",
        "2", "v2");

    var map3 = Flux.fromIterable(map1.entrySet())
        .concatWith(Flux.fromIterable(map2.entrySet()))
        .collectMap(Entry::getKey, Entry::getValue)
        .block();
    assertThat(map3).isEqualTo(expected);
  }

  @Test
  void collector_shortcuts() {
    var user1 = new User("uuid1", "john.doe@mail.org", "John Doe", Role.USER);
    var user2 = new User("uuid2", "jane.doe@mail.org", "Jane Doe", Role.USER);
    var user3 = new User("uuid3", "admin.doe@mail.org", "Admin Doe", Role.ADMIN);
    var users = List.of(user1, user2, user3);

    assertThat(
        StreamEx.of(users)
            .map(User::username)
            .toList())
        .containsExactly("john.doe@mail.org", "jane.doe@mail.org", "admin.doe@mail.org");
    assertThat(
        StreamEx.of(users)
            .groupingBy(User::role))
        .containsAllEntriesOf(
            Map.of(
                Role.USER, List.of(user1, user2),
                Role.ADMIN, List.of(user3)
            ));
    assertThat(StreamEx.of(1, 2, 3).joining("; "))
        .isEqualTo("1; 2; 3");

    // Note: Java 16 has toList
    assertThat(
        users.stream()
            .map(User::username)
            .collect(Collectors.toList())
    )
        .containsExactly("john.doe@mail.org", "jane.doe@mail.org", "admin.doe@mail.org");
    assertThat(
        users.stream()
            .collect(Collectors.groupingBy(User::role)))
        .containsAllEntriesOf(
            Map.of(
                Role.USER, List.of(user1, user2),
                Role.ADMIN, List.of(user3)
            ));

  }

  @Test
  void collectMap() {
    var users = IntStream.rangeClosed(1, 3)
        .mapToObj(it ->
            new User("id%s".formatted(it), "username%s".formatted(it), "fname", Role.USER))
        .toList();

    var usernameByIdMap = Flux.fromIterable(users)
        .collectMap(User::id, User::username)
        .block();
    assertThat(usernameByIdMap)
        .isEqualTo(Map.of(
            "id1", "username1",
            "id2", "username2",
            "id3", "username3"
        ));
  }

}
