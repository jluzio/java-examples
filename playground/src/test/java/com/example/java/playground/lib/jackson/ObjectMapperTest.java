package com.example.java.playground.lib.jackson;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.types.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class ObjectMapperTest {
  ObjectMapper mapper = new ObjectMapper();

  @Test
  void cloneData() {
    User someData = new User()
        .withId("id1")
        .withUsername("username1")
        .withAdditionalProperty("key1", "val1");

    var convertedData = mapper.convertValue(someData, User.class);
    assertThat(convertedData)
        .isEqualTo(someData)
        .isNotSameAs(someData);
    assertThat(convertedData.getAdditionalProperties())
        .isEqualTo(someData.getAdditionalProperties())
        .isNotSameAs(someData.getAdditionalProperties());
  }

}
