package com.example.java.playground.lib.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Slf4j
class MapStructTest {

  @Mapper
  interface PersonToUserMapper {

    @Mappings({
        @Mapping(target = "fullName", expression = "java(person.getFirstName() + person.getSurname())")
    })
    User toUser(Person person);
  }

  @Test
  void test() {
    PersonToUserMapper mapper = new MapStructTest$PersonToUserMapperImpl();

    Person person = Person.builder()
        .firstName("John")
        .surname("Doe")
        .email("mail@server.org")
        .build();

    User user = mapper.toUser(person);
    log.info("user: {}", user);
  }

}
