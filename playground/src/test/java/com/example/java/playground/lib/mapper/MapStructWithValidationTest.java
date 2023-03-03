package com.example.java.playground.lib.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.types.Person;
import com.example.types.User;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Email;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@SpringBootTest(classes = {
    MapStructWithValidationTest$ValidatedUserMapperImpl.class,
    MapStructWithValidationTest.Validations.class,
    ValidationAutoConfiguration.class
})
@Slf4j
class MapStructWithValidationTest {

  @Mapper(componentModel = "spring", uses = Validations.class)
  interface ValidatedUserMapper {

    // Note: for bean validation, a validation method needs to be called using the validation proxy.
    // In this scenario, it has to be a different class
    @Mapping(target = "email", source = "email", qualifiedByName = "validateEmail")
    User toUser(Person person);
  }

  @Component
  @Validated
  static class Validations {

    @Named("validateEmail")
    public String validateEmail(@Email String email) {
      return email;
    }
  }

  @Autowired
  ValidatedUserMapper mapper;


  @Test
  void toUser() {
    Person person = new Person()
        .withFirstName("John")
        .withSurname("Doe")
        .withEmail("mail@server.org");
    assertThat(mapper.toUser(person))
        .isNotNull()
        .satisfies(v -> log.info("user: {}", v));

    Person otherPerson = new Person()
        .withFirstName("John")
        .withSurname("Doe")
        .withEmail("invalid_email");
    assertThatThrownBy(() -> mapper.toUser(otherPerson))
        .isInstanceOf(ConstraintViolationException.class)
        .satisfies(v -> log.info("user: {}", v));
  }

}
