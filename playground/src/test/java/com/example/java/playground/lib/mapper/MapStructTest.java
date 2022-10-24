package com.example.java.playground.lib.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.types.Person;
import com.example.types.User;
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

    @Mapping(target = "id", ignore = true)
    void mergeWithoutId(@MappingTarget User target, User other);
  }

  UserMapper mapper = UserMapper.INSTANCE;

  @Test
  void toUser() {
    Person person = new Person()
        .withFirstName("John")
        .withSurname("Doe")
        .withEmail("mail@server.org");

    User user = mapper.toUser(person);
    log.info("user: {}", user);
  }

  @Test
  void merge() {
    User target = new User()
        .withId("1")
        .withUsername("dummy-user")
        .withEmail("mail@server.org");
    User other = new User()
        .withUsername("john.doe")
        .withFullName("John Doe");

    mapper.merge(target, other);

    assertThat(target).isEqualTo(new User()
        .withId("1")
        .withUsername("john.doe")
        .withEmail("mail@server.org")
        .withFullName("John Doe"));
  }

  @Test
  void mapUserRef() {
    var user = new User()
        .withId("1")
        .withUsername("john.doe")
        .withEmail("mail@server.org")
        .withFullName("John Doe");

    assertThat(mapper.mapUserRef(user))
        .isEqualTo(new User()
            .withId("1")
            .withUsername("john.doe"));
  }

  @Test
  void duplicateWithoutId() {
    var user = new User()
        .withId("1")
        .withUsername("john.doe")
        .withEmail("mail@server.org")
        .withFullName("John Doe");

    var target = new User()
        .withId("123");
    mapper.mergeWithoutId(target, user);
    assertThat(target)
        .isEqualTo(new User()
            .withId("123")
            .withUsername("john.doe")
            .withEmail("mail@server.org")
            .withFullName("John Doe"));
  }
}
