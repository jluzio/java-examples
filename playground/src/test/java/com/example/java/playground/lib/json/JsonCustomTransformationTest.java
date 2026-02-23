package com.example.java.playground.lib.json;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.java.playground.lib.json.schema.JsonPatchOperation;
import com.example.java.playground.lib.json.schema.JsonPatchOperation.CopyOperation;
import com.example.java.playground.lib.json.schema.JsonPatchOperation.MoveOperation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ContainerNode;
import tools.jackson.databind.node.ObjectNode;

@SpringBootTest
@Slf4j
class JsonCustomTransformationTest {

  @Configuration
  @EnableAutoConfiguration
  static class Config {

  }

  @Autowired
  ObjectMapper mapper;

  @Test
  void simple_test() {
    String input = """
        {
          "id": "id1",
          "name": "name1",
          "list": [1, 2, 3],
          "map": {
            "k1": "v1",
            "k2": "v2",
            "k3": "v3"
          }
        }
        """;

    var patchJson = """
        [
            {"op":"copy","from":"/id","path":"/id2"},
            {"op":"move","from":"/name","path":"/name2"}
        ]
        """;

    var inputJsonNode = mapper.readValue(input, JsonNode.class);
    log.info("input:\n{}", input);

    var patch = Arrays.stream(mapper.readValue(patchJson, JsonPatchOperation[].class)).toList();
    log.info("patch:\n{}", patch);

    var jsonPatch = new SimpleJsonPatch();

    var outputJsonNode = inputJsonNode.deepCopy();
    jsonPatch.apply(outputJsonNode, patch);

    var output = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(outputJsonNode);
    log.info("output:\n{}", output);
    @SuppressWarnings("unchecked")
    Map<String, Object> outputMap = mapper.convertValue(outputJsonNode, Map.class);

    assertThat(outputMap)
        .hasFieldOrPropertyWithValue("id", "id1")
        .hasFieldOrPropertyWithValue("id2", "id1")
        .hasFieldOrPropertyWithValue("name2", "name1")
        .doesNotContainKey("name")
    ;
  }

  public static class SimpleJsonPatch {

    record JsonNodePathInfo(String path, String parentPath, String nodeProperty) {

    }

    public void apply(JsonNode inputJsonNode, List<JsonPatchOperation> patch) {
      patch.forEach(it -> applyOperation(inputJsonNode, it));
    }

    private void applyOperation(JsonNode root, JsonPatchOperation patch) {
      switch (patch) {
        case CopyOperation op -> {
          var from = root.at(op.from());
          var toPathInfo = getPathInfo(op.path());
          var parentTo = getParentContainerNode(root, toPathInfo);

          setPropertyContainerNode(parentTo, toPathInfo.nodeProperty(), from);
        }
        case MoveOperation op -> {
          var from = root.at(op.from());
          var fromPathInfo = getPathInfo(op.from());
          var parentFrom = getParentContainerNode(root, fromPathInfo);
          var toPathInfo = getPathInfo(op.path());
          var parentTo = getParentContainerNode(root, toPathInfo);

          setPropertyContainerNode(parentTo, toPathInfo.nodeProperty(), from);
          removePropertyContainerNode(parentFrom, fromPathInfo.nodeProperty());
        }
        default -> throw new IllegalStateException("Unexpected value: " + patch);
      }
    }

    private JsonNodePathInfo getPathInfo(String path) {
      var parentPath = StringUtils.substringBeforeLast(path, "/");
      var nodeProperty = StringUtils.substringAfterLast(path, "/");
      return new JsonNodePathInfo(path, parentPath, nodeProperty);
    }

    private ContainerNode<?> getParentContainerNode(JsonNode root, JsonNodePathInfo pathInfo) {
      return (ContainerNode<?>) root.at(pathInfo.parentPath());
    }

    private void setPropertyContainerNode(ContainerNode<?> containerNode, String nodeProperty, JsonNode jsonNode) {
      switch (containerNode) {
        case ObjectNode node -> node.set(nodeProperty, jsonNode);
        case ArrayNode node -> node.set(Integer.parseInt(nodeProperty), jsonNode);
        default -> throw new IllegalStateException("Unexpected value: " + containerNode);
      }
    }

    private void removePropertyContainerNode(ContainerNode<?> containerNode, String nodeProperty) {
      switch (containerNode) {
        case ObjectNode node -> node.remove(nodeProperty);
        case ArrayNode node -> node.remove(Integer.parseInt(nodeProperty));
        default -> throw new IllegalStateException("Unexpected value: " + containerNode);
      }
    }
  }
}
