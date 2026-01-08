package com.example.java.playground.lib.json;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootTest
@Slf4j
class JsonPatchTest {

  record Customer(
      String id,
      String telephone,
      List<String> favorites,
      Map<String, Boolean> communicationPreferences) {

  }

  @Configuration
  static class Config {

    @Bean
    ObjectMapper jackson2ObjectMapper() {
      return JsonMapper.builder().build();
    }
  }

  @Autowired
  ObjectMapper mapper;

  @Test
  void simple_test() throws IOException, JsonPatchException {
    String initialValueJson = """
        {"telephone":"+1-555-12","favorites":["Milk","Eggs"],"communicationPreferences":{"post":true,"email":true}}
        """;
    String patchJson = """
        [
            {"op":"replace","path":"/telephone","value":"+1-555-56"},
            {"op":"add","path":"/favorites/0","value": "Bread"},
            {"op":"replace","path":"/communicationPreferences","value": {"post": false, "push": true}}
        ]
        """;
    JsonNode initialValueJsonNode = mapper.readValue(initialValueJson, JsonNode.class);
    log.info("initial value: {}", initialValueJson);

    JsonPatch patch = mapper.readValue(patchJson, JsonPatch.class);
    log.info("patch: {}", patch);

    JsonNode updateValueJsonNode = patch.apply(initialValueJsonNode);
    Customer updatedValue = mapper.treeToValue(updateValueJsonNode, Customer.class);
    log.info("updated value: {}", mapper.writeValueAsString(updatedValue));

    assertThat(updatedValue)
        .isEqualTo(new Customer(
            null,
            "+1-555-56",
            List.of("Bread", "Milk", "Eggs"),
            Map.of(
                "post", false,
                "push", true)));
  }

  @Test
  void simple_test_with_convert() throws IOException, JsonPatchException {
    String initialValueJson = """
        {"telephone":"+1-555-12","favorites":["Milk","Eggs"],"communicationPreferences":{"post":true,"email":true}}
        """;
    String patchJson = """
        [
            {"op":"replace","path":"/telephone","value":"+1-555-56"},
            {"op":"add","path":"/favorites/0","value": "Bread"}
        ]
        """;
    log.info("initial value: {}", initialValueJson);

    JsonPatch patch = mapper.readValue(patchJson, JsonPatch.class);
    log.info("patch: {}", patch);

    Customer initialValue = mapper.readValue(initialValueJson, Customer.class);
    JsonNode initialValueJsonNode = mapper.convertValue(initialValue, JsonNode.class);

    JsonNode updateValueJsonNode = patch.apply(initialValueJsonNode);
    Customer updatedValue = mapper.treeToValue(updateValueJsonNode, Customer.class);
    log.info("updated value: {}", mapper.writeValueAsString(updatedValue));

    assertThat(updatedValue)
        .isEqualTo(new Customer(
            null,
            "+1-555-56",
            List.of("Bread", "Milk", "Eggs"),
            Map.of(
                "post", true,
                "email", true)));
  }

}
