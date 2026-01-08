package com.example.java.playground.lib.commons.lang;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.function.Failable;
import org.apache.commons.lang3.stream.Streams;
import org.junit.jupiter.api.Test;
import tools.jackson.core.JacksonException;

@Slf4j
class StreamsTest {


  @Test
  void failable() throws JacksonException {
    Callable<String> goodDataSupplier = () -> "success";
    Callable<String> faultyDataSupplier = () -> {
      throw new IOException("Unable to read");
    };

    var defaultJavaWay = Stream.of(goodDataSupplier)
        .map(supplier -> {
          try {
            return supplier.call();
          } catch (Exception e) {
            return Failable.rethrow(e);
          }
        })
        .findFirst();
    assertThat(defaultJavaWay)
        .isPresent()
        .get().isEqualTo("success");

    assertThat(Streams.failableStream(List.of(goodDataSupplier))
        .map(Callable::call)
        .stream().findFirst())
        .isPresent()
        .get().isEqualTo("success");

    assertThatThrownBy(() ->
        Streams.failableStream(List.of(faultyDataSupplier))
            .map(Callable::call)
            .stream()
            .toList()
    )
        /**
         * checked exceptions become runtime exceptions
         * @see org.apache.commons.lang3.function.Failable#rethrow(Throwable)
         **/
        .isInstanceOf(RuntimeException.class)
        // with this specific checked exception, it becomes UncheckedIOException
        .isInstanceOf(UncheckedIOException.class)
        .hasCauseInstanceOf(IOException.class);
  }

}
