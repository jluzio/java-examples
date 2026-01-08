package com.example.java.playground.lib.jackson;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.autoconfigure.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;

@SpringBootTest(classes = {JacksonAutoConfiguration.class})
@Log4j2
class DeserializeWithBuilderTest {

  @Autowired
  ObjectMapper objectMapper;

  @Builder
  record SimpleData(String value) {

  }

  @Builder
  @JsonDeserialize(builder = Data.DataBuilder.class)
  record Data(String value) {

    @JsonPOJOBuilder(withPrefix = "")
    public static class DataBuilder {

      DataBuilder valueBase64(String value) {
        byte[] bytes = Base64.getDecoder().decode(value);
        String string = new String(bytes, StandardCharsets.UTF_8);
        return value(string);
      }
    }
  }

  @Test
  void test_without_builder() throws IOException {
    var json = """
        {"value": "test"}
        """;
    var data = objectMapper.readValue(json, SimpleData.class);
    log.debug(data);
    assertThat(data)
        .isEqualTo(new SimpleData("test"));
  }

  @Test
  void test_with_builder() throws IOException {
    String valueBase64 = Base64.getEncoder().encodeToString("test".getBytes(StandardCharsets.UTF_8));
    var json = """
        {"valueBase64": "%s"}
        """.formatted(valueBase64);
    var data = objectMapper.readValue(json, Data.class);
    log.debug(data);
    assertThat(data)
        .isEqualTo(new Data("test"));
  }

}
