package com.example.java.playground.lib.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.types.User;
import java.util.List;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Slf4j
class CollectionTest {

  @Mapper
  interface CollectionMapper {

    List<String> mapUsers(List<User> users);

    default String mapUser(User user) {
      return user.getUsername();
    }

  }

  @Test
  void collection() {
    CollectionMapper mapper = Mappers.getMapper(CollectionMapper.class);
    var expectedUsernames = IntStream.rangeClosed(1, 5)
        .mapToObj("username-%s"::formatted)
        .toList();
    var users = expectedUsernames.stream()
        .map(username -> new User().withUsername(username))
        .toList();
    var usernames = mapper.mapUsers(users);
    log.info("usernames: {}", usernames);
    assertThat(usernames)
        .isEqualTo(expectedUsernames);
  }

}
