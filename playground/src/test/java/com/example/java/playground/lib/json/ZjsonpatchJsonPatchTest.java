package com.example.java.playground.lib.json;

import static org.assertj.core.api.Assertions.assertThat;

import com.flipkart.zjsonpatch.Jackson3JsonPatch;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.autoconfigure.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/**
 * Jackson 3 and 2 implementation
 * @see <a href="https://github.com/flipkart-incubator/zjsonpatch">https://github.com/flipkart-incubator/zjsonpatch</a>
 */
@SpringBootTest(classes = JacksonAutoConfiguration.class)
@Slf4j
class ZjsonpatchJsonPatchTest {

  record Customer(
      String id,
      String telephone,
      List<String> favorites,
      Map<String, Boolean> communicationPreferences) {

  }

  @Autowired
  ObjectMapper mapper;

  @Test
  void simple_test() {
    var initialValueJson = """
        {"telephone":"+1-555-12","favorites":["Milk","Eggs"],"communicationPreferences":{"post":true,"email":true}}
        """;
    var patchJson = """
        [
            {"op":"replace","path":"/telephone","value":"+1-555-56"},
            {"op":"add","path":"/favorites/0","value": "Bread"},
            {"op":"replace","path":"/communicationPreferences","value": {"post": false, "push": true}}
        ]
        """;
    var initialValueJsonNode = mapper.readValue(initialValueJson, JsonNode.class);
    log.info("initial value: {}", initialValueJson);

    var patch = mapper.readValue(patchJson, JsonNode.class);
    log.info("patch: {}", patch);

    var updateValueJsonNode = Jackson3JsonPatch.apply(patch, initialValueJsonNode);
    var updatedValue = mapper.treeToValue(updateValueJsonNode, Customer.class);
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
