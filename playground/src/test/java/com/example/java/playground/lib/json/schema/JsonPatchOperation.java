package com.example.java.playground.lib.json.schema;

import com.example.java.playground.lib.json.schema.JsonPatchOperation.AddOperation;
import com.example.java.playground.lib.json.schema.JsonPatchOperation.CopyOperation;
import com.example.java.playground.lib.json.schema.JsonPatchOperation.MoveOperation;
import com.example.java.playground.lib.json.schema.JsonPatchOperation.RemoveOperation;
import com.example.java.playground.lib.json.schema.JsonPatchOperation.ReplaceOperation;
import com.example.java.playground.lib.json.schema.JsonPatchOperation.TestOperation;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import tools.jackson.databind.JsonNode;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "op")
@JsonSubTypes({
    @Type(name = "add", value = AddOperation.class),
    @Type(name = "copy", value = CopyOperation.class),
    @Type(name = "move", value = MoveOperation.class),
    @Type(name = "remove", value = RemoveOperation.class),
    @Type(name = "replace", value = ReplaceOperation.class),
    @Type(name = "test", value = TestOperation.class)
})
public sealed interface JsonPatchOperation {

  record RemoveOperation(String op, String path) implements JsonPatchOperation {

  }

  record AddOperation(String op, String path, JsonNode value) implements JsonPatchOperation {

  }
  record ReplaceOperation(String op, String path, JsonNode value) implements JsonPatchOperation {

  }
  record TestOperation(String op, String path, JsonNode value) implements JsonPatchOperation {

  }

  record CopyOperation(String op, String from, String path) implements JsonPatchOperation {

  }
  record MoveOperation(String op, String from, String path) implements JsonPatchOperation {

  }

}
