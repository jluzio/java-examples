package com.example.java.playground.lib.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.types.Person;
import com.example.types.User;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootTest
class AbstractMapperTest {

  @Mapper(componentModel = ComponentModel.SPRING)
  public static abstract class PersonMapper {
    @Setter(onMethod_ = {@Autowired})
    private PersonNamesMapper personNamesMapper;

    public Person mapPerson(User user) {
      var person = mapPerson(new Person(), user);
      var names = personNamesMapper.names(user.getFullName());
      person.setFirstName(names.getLeft());
      person.setSurname(names.getRight());
      return person;
    }

    abstract protected Person mapPerson(@MappingTarget Person person, User user);

  }

  @Configuration
  @Import({AbstractMapperTest$PersonMapperImpl.class, PersonNamesMapper.class})
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
