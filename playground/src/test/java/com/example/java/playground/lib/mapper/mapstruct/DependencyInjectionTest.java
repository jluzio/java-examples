package com.example.java.playground.lib.mapper.mapstruct;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.types.Person;
import com.example.types.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Slf4j
class DependencyInjectionTest {

  @Mapper(componentModel = "spring")
  interface OtherMapper {

    @Named("otherTrim")
    default String trim(String value) {
      return StringUtils.trim(value);
    }
  }

  @Mapper(componentModel = "spring")
  interface OtherCustomMapper {

    @Named("otherCustomTrim")
    default String trim(String value) {
      return StringUtils.trim(value);
    }
  }

  @Mapper(
      componentModel = "spring",
      uses = {OtherMapper.class, OtherCustomMapper.class},
      injectionStrategy = InjectionStrategy.CONSTRUCTOR
  )
  interface DIMapper {

    @Mapping(source = "email", target = "email", qualifiedByName = "otherTrim")
    @Mapping(source = "firstName", target = "fullName", qualifiedByName = "otherCustomTrim")
    User toUser(Person person);
  }

  @Test
  void test() {
    DIMapper mapper = new DependencyInjectionTest$DIMapperImpl(
        new DependencyInjectionTest$OtherMapperImpl(),
        new DependencyInjectionTest$OtherCustomMapperImpl()
    );

    Person person = new Person()
        .withFirstName("John   ")
        .withSurname("Doe   ")
        .withEmail("mail@server.org     ");

    User user = mapper.toUser(person);
    log.info("user: {}", user);
    assertThat(user)
        .isNotNull()
        .hasFieldOrPropertyWithValue("email", "mail@server.org")
        .hasFieldOrPropertyWithValue("fullName", "John");
  }

}
