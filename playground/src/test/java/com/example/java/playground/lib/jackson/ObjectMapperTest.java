package com.example.java.playground.lib.jackson;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import com.example.types.User;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tools.jackson.databind.ObjectMapper;

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

  @Test
  void closeStream() throws IOException {
    User someData = new User()
        .withId("id1")
        .withUsername("username1")
        .withAdditionalProperty("key1", "val1");
    var jsonString = mapper.writeValueAsString(someData);
    var jsonInputStream = Mockito.spy(new ByteArrayInputStream(jsonString.getBytes()));
    var convertedData = mapper.readValue(jsonInputStream, User.class);
    assertThat(convertedData)
        .isEqualTo(someData);
    verify(jsonInputStream).close();
  }

}
