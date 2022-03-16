package com.example.java.playground.features.lang;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

@Slf4j
class ParameterTest {

  static class SampleClass {

    public void foobar(String foo, String bar) {
      log.info("foo: {} | bar: {}", foo, bar);
    }
  }

  @Test
  void test() throws NoSuchMethodException {
    Method method = SampleClass.class.getMethod("foobar", String.class, String.class);
    var parameters = Flux.fromArray(method.getParameters());
    log.info("{}", parameters.map(Parameter::getName).collectList().block());
    assertThat(parameters.map(Parameter::getName).collectList().block())
        .isEqualTo(List.of("foo", "bar"));
  }

}
