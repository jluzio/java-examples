package com.example.java.playground.features.lang;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

@Slf4j
class OptionalTest {

  @Test
  void test_basic() {
    var optionalNonNull = of("test");
    assertThat(optionalNonNull.filter(s -> s.length() > 2)).isPresent();
    assertThat(optionalNonNull.map("value is '%s'"::formatted))
        .isPresent().get().isEqualTo("value is 'test'");

    Optional<String> optionalNull = ofNullable(null);

    assertThat(optionalNull).isEqualTo(Optional.empty());

    log.info("optional.orElse: {}", optionalNull.orElse("other"));
    log.info("optional.or: {}", optionalNull.or(() -> of(RandomStringUtils.random(10))));
    log.info("optional.orElseGet: {}", optionalNull.orElseGet(() -> "other"));
    assertThatThrownBy(() -> optionalNull.orElseThrow())
        .isInstanceOf(NoSuchElementException.class);

    log.info("optional.filter: {}", optionalNull.filter(v -> v.length() > 2));
    log.info("optional.map: {}", optionalNull.map(String::length));

    optionalNull.ifPresentOrElse(v -> log.info("present: {}", v), () -> log.info("empty"));
    optionalNonNull.ifPresentOrElse(v -> log.info("present: {}", v), () -> log.info("empty"));

    log.info("stream action (count): {} | {}", optionalNull.stream().count(),
        optionalNonNull.stream().count());
  }

  @Test
  void test_collection() {
    var optional = of(List.of("aaa", "bb", "c"));
    var lengths = optional.stream()
        .flatMap(Collection::stream)
        .map(String::length)
        .collect(Collectors.toList());
    assertThat(lengths)
        .containsExactly(3, 2, 1);
  }

  record Parent(String id, Child child) {

  }

  record Child(String id) {

  }

  @Test
  void nullableMappings() {
    Function<Parent, Optional<String>> getChildId = p -> of(p)
        .map(Parent::child)
        .map(Child::id);

    assertThat(getChildId.apply(new Parent("p1", null)))
        .isNotPresent();

    assertThat(getChildId.apply(new Parent("p2", new Child(null))))
        .isNotPresent();

    assertThat(getChildId.apply(new Parent("p3", new Child("c3"))))
        .isPresent();
  }
}
