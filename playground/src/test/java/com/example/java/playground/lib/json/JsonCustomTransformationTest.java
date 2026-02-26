package com.example.java.playground.lib.json;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.java.playground.lib.json.schema.JsonPatchOperation;
import com.example.java.playground.lib.json.schema.JsonPatchOperation.AddOperation;
import com.example.java.playground.lib.json.schema.JsonPatchOperation.CopyOperation;
import com.example.java.playground.lib.json.schema.JsonPatchOperation.MoveOperation;
import com.example.java.playground.lib.json.schema.JsonPatchOperation.RemoveOperation;
import com.example.java.playground.lib.json.schema.JsonPatchOperation.ReplaceOperation;
import com.example.java.playground.lib.json.schema.JsonPatchOperation.TestOperation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
          "unused": "del-me",
          "list": ["1", "2", "3"],
          "object_list": [
            { "id": "id1", "value": "obj1" },
            { "id": "id2", "value": "obj2" }
          ],
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
            {"op":"move","from":"/name","path":"/name2"},
            {"op":"copy","from":"/name2","path":"/list/1"},
            {"op":"copy","from":"/object_list/0","path":"/object_first"},
            {"op":"copy","from":"/object_list","path":"/new_object_list"},
            {"op":"remove","path":"/unused"},
            {"op":"remove","path":"/new_object_list/0"},
            {"op":"add","path":"/added_object","value":{"key":"add_key","value":"add_val"}},
            {"op":"replace","path":"/added_object/value","value":"replace_val"},
            {"op":"test","path":"/added_object/value","value":"replace_val"}
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
        .doesNotContainKey("unused")
    ;

    assertThat(outputJsonNode.at("/object_first/id").asString())
        .isEqualTo("id1");
    assertThat(outputJsonNode.at("/new_object_list").size())
        .isEqualTo(1);
    assertThat(outputJsonNode.at("/new_object_list/0/id").asString())
        .isEqualTo("id2");
    assertThat(outputJsonNode.at("/added_object/key").asString())
        .isEqualTo("add_key");
    assertThat(outputJsonNode.at("/added_object/value").asString())
        .isEqualTo("replace_val");
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
          var toParent = getContainerNode(root, toPathInfo.parentPath());

          setPropertyContainerNode(toParent, toPathInfo.nodeProperty(), from);
        }
        case MoveOperation op -> {
          var from = root.at(op.from());
          var fromPathInfo = getPathInfo(op.from());
          var fromParent = getContainerNode(root, fromPathInfo.parentPath());
          var toPathInfo = getPathInfo(op.path());
          var toParent = getContainerNode(root, toPathInfo.parentPath());

          setPropertyContainerNode(toParent, toPathInfo.nodeProperty(), from);
          removePropertyContainerNode(fromParent, fromPathInfo.nodeProperty());
        }
        case RemoveOperation op -> {
          var toPathInfo = getPathInfo(op.path());
          var toParent = getContainerNode(root, toPathInfo.parentPath());

          removePropertyContainerNode(toParent, toPathInfo.nodeProperty());
        }
        case AddOperation op -> {
          var toPathInfo = getPathInfo(op.path());
          var toParent = getContainerNode(root, toPathInfo.parentPath());

          setPropertyContainerNode(toParent, toPathInfo.nodeProperty(), op.value());
        }
        case ReplaceOperation op -> {
          var toPathInfo = getPathInfo(op.path());
          var toParent = getContainerNode(root, toPathInfo.parentPath());

          setPropertyContainerNode(toParent, toPathInfo.nodeProperty(), op.value());
        }
        case TestOperation op -> {
          var to = root.at(op.path());
          // basic check
          if (!Objects.equals(op.value(), to)) {
            throw new IllegalStateException("Test failed :: op=%s | to=%s".formatted(op, to));
          }
        }
        default -> throw new UnsupportedOperationException("Unsupported: " + patch);
      }
    }

    private JsonNodePathInfo getPathInfo(String path) {
      var parentPath = StringUtils.substringBeforeLast(path, "/");
      var nodeProperty = StringUtils.substringAfterLast(path, "/");
      return new JsonNodePathInfo(path, parentPath, nodeProperty);
    }

    private ContainerNode<?> getContainerNode(JsonNode root, String path) {
      if (root.at(path) instanceof ContainerNode<?> node) {
        return node;
      } else {
        throw new IllegalStateException("Unable to get ContainerNode for path: " + path);
      }
    }

    private void setPropertyContainerNode(ContainerNode<?> containerNode, String nodeProperty, JsonNode jsonNode) {
      switch (containerNode) {
        case ObjectNode node -> node.set(nodeProperty, jsonNode);
        case ArrayNode node -> node.set(getArrayIndex(nodeProperty), jsonNode);
        default -> throw new IllegalStateException("Unexpected value: " + containerNode);
      }
    }

    private void removePropertyContainerNode(ContainerNode<?> containerNode, String nodeProperty) {
      switch (containerNode) {
        case ObjectNode node -> node.remove(nodeProperty);
        case ArrayNode node -> node.remove(getArrayIndex(nodeProperty));
        default -> throw new IllegalStateException("Unexpected value: " + containerNode);
      }
    }

    private int getArrayIndex(String nodeProperty) {
      return Integer.parseInt(nodeProperty);
    }
  }
}
