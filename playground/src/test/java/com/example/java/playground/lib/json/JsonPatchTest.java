package com.example.java.playground.lib.json;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = JacksonAutoConfiguration.class)
@Slf4j
class JsonPatchTest {

  record Customer(
      String id,
      String telephone,
      List<String> favorites,
      Map<String, Boolean> communicationPreferences) {

  }

  @Autowired
  private ObjectMapper mapper;

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

    log.debug("initial value: {}", initialValueJson);

    JsonPatch patch = mapper.readValue(patchJson, JsonPatch.class);
    log.debug("patch: {}", patch);

    JsonNode node = mapper.readValue(initialValueJson, JsonNode.class);
    JsonNode patched = patch.apply(node);

    Customer customer = mapper.treeToValue(patched, Customer.class);
    log.debug("patched value: {}", customer);
    assertThat(customer)
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

    log.debug("initial value: {}", initialValueJson);

    JsonPatch patch = mapper.readValue(patchJson, JsonPatch.class);
    log.debug("patch: {}", patch);

    Customer initialValue = mapper.readValue(initialValueJson, Customer.class);
    JsonNode node = mapper.convertValue(initialValue, JsonNode.class);

    JsonNode patched = patch.apply(node);

    Customer customer = mapper.treeToValue(patched, Customer.class);
    log.debug("patched value: {}", customer);
    assertThat(customer)
        .isEqualTo(new Customer(
            null,
            "+1-555-56",
            List.of("Bread", "Milk", "Eggs"),
            Map.of(
                "post", true,
                "email", true)));
  }

}
