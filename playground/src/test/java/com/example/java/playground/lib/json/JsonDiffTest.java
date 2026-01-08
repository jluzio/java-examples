package com.example.java.playground.lib.json;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.diff.JsonDiff;
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
class JsonDiffTest {

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
  private ObjectMapper mapper;

  @Test
  void simple_test() throws IOException, JsonPatchException {
    String initialValueJson = """
        {"telephone":"+1-555-12","favorites":["Milk","Eggs"],"communicationPreferences":{"post":true,"email":true}}
        """;
    String updatedValueJson = """
        {"id":null,"telephone":"+1-555-56","favorites":["Bread","Milk","Eggs"],"communicationPreferences":{"post":false,"push":true}}
        """;
    String expectedPatchJson = """
        [
            {"op":"replace","path":"/telephone","value":"+1-555-56"},
            {"op":"add","path":"/favorites/0","value": "Bread"},
            {"op":"replace","path":"/communicationPreferences","value": {"post": false, "push": true}}
        ]
        """;
    JsonNode initialValueJsonNode = mapper.readValue(initialValueJson, JsonNode.class);
    JsonNode updatedValueJsonNode = mapper.readValue(updatedValueJson, JsonNode.class);
    log.info("initial value: {}", initialValueJson);
    log.info("updated value: {}", updatedValueJson);

    log.debug("patch.1: {}", JsonDiff.asJson(updatedValueJsonNode, initialValueJsonNode));
    log.debug("patch.2: {}", JsonDiff.asJson(updatedValueJsonNode, initialValueJsonNode));
    log.debug("patch.3: {}", JsonDiff.asJsonPatch(initialValueJsonNode, updatedValueJsonNode));
    log.debug("patch.4: {}", JsonDiff.asJsonPatch(initialValueJsonNode, updatedValueJsonNode));

    JsonPatch patch = JsonDiff.asJsonPatch(initialValueJsonNode, updatedValueJsonNode);
    log.info("patch: {}", patch);

    assertThat(patch).isNotNull();

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

}
