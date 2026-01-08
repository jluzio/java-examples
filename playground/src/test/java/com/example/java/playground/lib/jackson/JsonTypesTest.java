package com.example.java.playground.lib.jackson;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.autoconfigure.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest(classes = JacksonAutoConfiguration.class)
@Slf4j
class JsonTypesTest {

  @Autowired
  ObjectMapper objectMapper;

  @Data
  @NoArgsConstructor
  // to avoid Jackson 3 using the all arguments constructor instead of no arguments constructor
  @AllArgsConstructor(onConstructor_ = @JsonIgnore)
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
  void test_maps() throws JacksonException {
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
