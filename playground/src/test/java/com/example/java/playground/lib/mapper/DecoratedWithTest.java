package com.example.java.playground.lib.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.types.Person;
import com.example.types.User;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@SpringBootTest
class DecoratedWithTest {

  @Mapper(componentModel = ComponentModel.SPRING)
  @DecoratedWith(PersonMapperDelegate.class)
  interface PersonMapper {

    Person mapPerson(User user);

  }

  @Component
  static class PersonMapperDelegate implements PersonMapper {

    @Setter(onMethod_ = {@Autowired, @Qualifier("delegate")})
    private PersonMapper delegate;
    @Setter(onMethod_ = {@Autowired})
    private PersonNamesMapper personNamesMapper;

    @Override
    public Person mapPerson(User user) {
      var person = delegate.mapPerson(user);
      var names = personNamesMapper.names(user.getFullName());
      person.setFirstName(names.getLeft());
      person.setSurname(names.getRight());
      return person;
    }
  }

  @Configuration
  @Import({
      DecoratedWithTest$PersonMapperImpl.class,
      DecoratedWithTest$PersonMapperImpl_.class,
      DecoratedWithTest.PersonMapperDelegate.class,
      PersonNamesMapper.class
  })
  static class Config {

  }

  @Autowired
  PersonMapper mapper;

  @Test
  void test() {
    assertThat(mapper.mapPerson(
            new User()
                .withId("id")
                .withFullName("firstName lastName")
        )
    )
        .isEqualTo(
            new Person()
                .withId("id")
                .withFirstName("firstName")
                .withSurname("lastName")
        );
  }

}
