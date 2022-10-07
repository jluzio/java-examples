package com.example.java.playground.lib.commons.lang3;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UncheckedIOException;
import java.util.List;
import org.apache.commons.lang3.function.Failable;
import org.apache.commons.lang3.stream.Streams;
import org.junit.jupiter.api.Test;

class StreamsTest {

  private record Data(String name) {

  }

  private final ObjectMapper objectMapper = new ObjectMapper();


  @Test
  void failable() throws JsonProcessingException {
    var exampleData = new Data("name-1");
    String exampleDataJson = objectMapper.writeValueAsString(exampleData);

    var defaultJavaWay = List.of(exampleDataJson).stream()
        .map(v -> {
          try {
            return objectMapper.readValue(v, Data.class);
          } catch (JsonProcessingException e) {
            return Failable.rethrow(e);
          }
        })
        .findFirst();
    assertThat(defaultJavaWay)
        .isPresent()
        .get().isEqualTo(exampleData);

    assertThat(Streams.stream(List.of(exampleDataJson))
        .map(v -> objectMapper.readValue(v, Data.class))
        .stream().findFirst())
        .isPresent()
        .get().isEqualTo(exampleData);

    assertThatThrownBy(() ->
        Streams.stream(List.of("bad-data"))
            .map(v -> objectMapper.readValue(v, Data.class)))
        /**
         * checked exceptions become runtime exceptions
         * @see org.apache.commons.lang3.function.Failable#rethrow(Throwable)
         **/
        .isInstanceOf(RuntimeException.class)
        // with this specific checked exception, it becomes UncheckedIOException
        .isInstanceOf(UncheckedIOException.class)
        .getCause().isInstanceOf(JsonProcessingException.class);

  }

}
