package com.example.java.playground.lib.jackson;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

@SpringBootTest(classes = JacksonAutoConfiguration.class)
@Slf4j
class JsonTypesTest {

  @Autowired
  ObjectMapper objectMapper;

  @Data
  @RequiredArgsConstructor
  @AllArgsConstructor
  @Builder
  static class MapsHolder {

    private Map<String, String> map;
    private Map<String, List<String>> simpleMultiValueMap;
    private MultiValueMap<String, String> multiValueMap;

    public void setMultiValueMap(MultiValueMap<String, String> multiValueMap) {
      this.multiValueMap = multiValueMap;
    }

    @JsonProperty
    public void setMultiValueMap(Map<String, List<String>> multiValueMap) {
      this.multiValueMap = CollectionUtils.toMultiValueMap(multiValueMap);
    }
  }

  @Test
  void test_maps() throws JsonProcessingException {
    var input = MapsHolder.builder()
        .map(Map.of("k1", "v1"))
        .simpleMultiValueMap(Map.of("k1", List.of("v1", "v2")))
        .multiValueMap(CollectionUtils.toMultiValueMap(Map.of("k1", List.of("v1", "v2"))))
        .build();

    String json = objectMapper.writeValueAsString(input);
    log.debug("json: {}", json);
    assertThat(json).isNotEmpty();

    MapsHolder output = objectMapper.readValue(json, MapsHolder.class);
    assertThat(output).isEqualTo(input);
  }

}
