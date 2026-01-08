package com.example.java.playground.lib.jackson;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.autoconfigure.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest(classes = {JacksonAutoConfiguration.class})
@Slf4j
class UnknownTypeTest {

  @Autowired
  ObjectMapper objectMapper;

  @Test
  void write() throws JacksonException {
    var parent1 = new ParentObject("parent1", new Foo("foo1"));
    log.debug("{}", objectMapper.writeValueAsString(parent1));

    var parent2 = new ParentObject("parent2", new Bar("bar1"));
    log.debug("{}", objectMapper.writeValueAsString(parent2));

    var parent3 = new ParentObject("parent3", new Foo[]{
        new Foo("foo1"), new Foo("foo2")
    });
    log.debug("{}", objectMapper.writeValueAsString(parent3));
  }

  @Test
  void read() throws JacksonException {
    var json1 = """
        {"name":"parent1","child":{"foo":"foo1"}}
            """;
    var json2 = """
        {"name":"parent2","child":{"bar":"bar1"}}
            """;
    var json3 = """
        {"name":"parent3","child":[{"foo":"foo1"},{"foo":"foo2"}]}
            """;

    var parent1 = objectMapper.readValue(json1, ParentObject.class);
    log.debug("{}", parent1);
    assertThat(parent1.getChild())
        .isInstanceOf(Map.class);
    var child1 = objectMapper.convertValue(parent1.getChild(), Foo.class);
    log.debug("{} :: {}", child1.getClass().getSimpleName(), child1);

    var parent2 = objectMapper.readValue(json2, ParentObject.class);
    log.debug("{}", parent2);
    assertThat(parent2.getChild())
        .isInstanceOf(Map.class);
    var child2 = objectMapper.convertValue(parent2.getChild(), Bar.class);
    log.debug("{} :: {}", child2.getClass().getSimpleName(), child2);

    var parent3 = objectMapper.readValue(json3, ParentObject.class);
    log.debug("{}", parent3);
    assertThat(parent3.getChild())
        .isInstanceOf(List.class);
    var child3 = objectMapper.convertValue(parent3.getChild(), Foo[].class);
    log.debug("{} :: {}", child3.getClass().getSimpleName(), child3);
  }


  @Data
  @RequiredArgsConstructor
  @AllArgsConstructor
  public static class ParentObject {

    private String name;
    private Object child;

  }

  @Data
  @RequiredArgsConstructor
  @AllArgsConstructor
  public static class Foo {

    private String foo;

  }

  @Data
  @RequiredArgsConstructor
  @AllArgsConstructor
  public static class Bar {

    private String bar;

  }

}
