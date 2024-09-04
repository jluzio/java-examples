package com.example.java.playground.lib.mapper.mapstruct;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.types.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

class InheritReverseConfigurationTest {

  record AnotherUser(String anotherUsername, String anotherFullName) {

  }

  @Mapper
  interface InheritReverseConfigurationMapper {

    InheritReverseConfigurationMapper INSTANCE = Mappers.getMapper(
        InheritReverseConfigurationMapper.class);

    @Mapping(target = "username", source = "anotherUsername")
    @Mapping(target = "fullName", source = "anotherFullName")
    User toUser(AnotherUser anotherUser);

    @InheritInverseConfiguration
    AnotherUser toAnotherUser(User user);
  }

  @Test
  void test() {
    var mapper = InheritReverseConfigurationMapper.INSTANCE;
    assertThat(mapper.toAnotherUser(new User()
        .withUsername("username1")
        .withFullName("fullName1")))
        .isEqualTo(new AnotherUser("username1", "fullName1"));
  }

}
