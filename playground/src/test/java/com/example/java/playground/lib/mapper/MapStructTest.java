package com.example.java.playground.lib.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Slf4j
class MapStructTest {

  @Mapper
  interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "fullName", expression = "java(person.getFirstName() + person.getSurname())")
    User toUser(Person person);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "fullName", ignore = true)
    User mapUserRef(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void merge(@MappingTarget User target, User other);
  }

  UserMapper mapper = UserMapper.INSTANCE;

  @Test
  void toUser() {
    Person person = Person.builder()
        .firstName("John")
        .surname("Doe")
        .email("mail@server.org")
        .build();

    User user = mapper.toUser(person);
    log.info("user: {}", user);
  }

  @Test
  void merge() {
    User target = User.builder()
        .username("dummy-user")
        .email("mail@server.org")
        .build();
    User other = User.builder()
        .username("john.doe")
        .fullName("John Doe")
        .build();

    mapper.merge(target, other);

    assertThat(target).isEqualTo(User.builder()
        .username("john.doe")
        .email("mail@server.org")
        .fullName("John Doe")
        .build());
  }

  @Test
  void mapUserRef() {
    var user = User.builder()
        .username("john.doe")
        .email("mail@server.org")
        .fullName("John Doe")
        .build();

    assertThat(mapper.mapUserRef(user))
        .isEqualTo(User.builder()
            .username("john.doe")
            .build());
  }
}
