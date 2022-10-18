package com.example.java.playground.lib.jackson;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.java.playground.lib.jackson.JsonViewTest.Views.Detailed;
import com.example.java.playground.lib.jackson.JsonViewTest.Views.Internal;
import com.example.java.playground.lib.jackson.JsonViewTest.Views.Public;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = JacksonAutoConfiguration.class)
class JsonViewTest {

  class Views {

    interface Public {

    }

    interface Detailed extends Public {

    }

    interface Internal {

    }
  }

  /**
   * JsonView can be applied directly or with an interface
   */
  @JsonView(Public.class)
  interface ModelPublicView {

  }

  @Data
  @RequiredArgsConstructor
  @AllArgsConstructor
  @Builder
  static class User implements ModelPublicView {

    private String id;
    private String username;
    @JsonView(Detailed.class)
    private String email;
    @JsonView(Internal.class)
    private String password;

  }

  @Data
  @RequiredArgsConstructor
  @AllArgsConstructor
  @Builder
  static class Todo {

    private String id;
    private String title;
    @JsonView(Detailed.class)
    private String description;

  }

  @Autowired
  ObjectMapper objectMapper;


  @Test
  void views() throws JsonProcessingException {
    var user = User.builder()
        .id("1")
        .username("john.doe")
        .email("john.doe@mail.org")
        .password("12345")
        .build();

    var userJson = objectMapper
        .writeValueAsString(user);
    var userDetailedJson = objectMapper
        .writerWithView(Detailed.class)
        .forType(User.class)
        .writeValueAsString(user);
    var userPublicJson = objectMapper
        .writerWithView(Public.class)
        .forType(User.class)
        .writeValueAsString(user);
    assertThat(userDetailedJson)
        .isNotEqualTo(userJson)
        .isNotEqualTo(userPublicJson);

    var userDetailed = objectMapper.readValue(userDetailedJson, User.class);
    var userDetailedAlternative = objectMapper
        .readerWithView(Detailed.class)
        .forType(User.class)
        .readValue(userJson);
    assertThat(userDetailed)
        .isEqualTo(User.builder()
            .id("1")
            .username("john.doe")
            .email("john.doe@mail.org")
            .build())
        .isEqualTo(userDetailedAlternative);

    var userPublic = objectMapper.readValue(userPublicJson, User.class);
    assertThat(userPublic)
        .isEqualTo(User.builder()
            .id("1")
            .username("john.doe")
            .build());

    var userDefault = objectMapper.readValue(userJson, User.class);
    assertThat(userDefault)
        .isEqualTo(user);
  }

  @Test
  void defaultView() throws JsonProcessingException {
    ObjectMapper objectMapperWithDefaultView = JsonMapper.builder()
        .enable(MapperFeature.DEFAULT_VIEW_INCLUSION)
        .build();
    ObjectMapper objectMapperWithoutDefaultView = JsonMapper.builder()
        .disable(MapperFeature.DEFAULT_VIEW_INCLUSION)
        .build();

    var todo = Todo.builder()
        .id("1")
        .title("todo1")
        .description("some description")
        .build();

    var detailedFieldsAutowiredMapper = objectMapper
        .writerWithView(Detailed.class)
        .writeValueAsString(todo);
    var detailedFieldsWithDefaultView = objectMapperWithDefaultView
        .writerWithView(Detailed.class)
        .writeValueAsString(todo);
    var detailedFieldsWithoutDefaultView = objectMapperWithoutDefaultView
        .writerWithView(Detailed.class)
        .writeValueAsString(todo);

    var defaultFieldsAutowiredMapper = objectMapper
        .writeValueAsString(todo);
    var defaultFieldsWithDefaultView = objectMapperWithDefaultView
        .writeValueAsString(todo);
    var defaultFieldsWithoutDefaultView = objectMapperWithoutDefaultView
        .writeValueAsString(todo);

    assertThat(detailedFieldsWithDefaultView)
        .isNotEqualTo(detailedFieldsWithoutDefaultView);
    assertThat(detailedFieldsWithoutDefaultView)
        .isEqualTo(detailedFieldsAutowiredMapper);

    assertThat(defaultFieldsAutowiredMapper)
        .isEqualTo(defaultFieldsWithDefaultView)
        .isEqualTo(defaultFieldsWithoutDefaultView);

    assertThat(objectMapper.readValue(detailedFieldsWithDefaultView, Todo.class))
        .isEqualTo(todo);
    assertThat(objectMapper.readValue(detailedFieldsWithoutDefaultView, Todo.class))
        .isEqualTo(Todo.builder()
            .description("some description")
            .build());
  }

}
