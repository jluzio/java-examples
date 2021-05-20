package com.example.java.playground.lib.vavr;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.isIn;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.vavr.MatchError;
import io.vavr.Predicates;
import io.vavr.control.Option;
import org.junit.jupiter.api.Test;

class MatchTest {

  @Test
  void match_default() {
    int i = 2;
    String s = Match(i).of(
        Case($(1), "one"),
        Case($(2), "two"),
        Case($(), "?")
    );
    assertThat(s).isEqualTo("two");

    String s2 = Match(i).of(
        Case($(isIn(1, 2)), "one or two"),
        Case($(), "?")
    );
    assertThat(s2).isEqualTo("one or two");
  }

  @Test
  void match_not_exhaustive() {
    int i = 2;
    assertThatThrownBy(
        () ->
            Match(i).of(
                Case($(1), "one")
            ))
        .isInstanceOf(MatchError.class);
  }

  @Test
  void match_option_not_exhaustive() {
    int i = 2;
    Option<String> s = Match(i).option(
        Case($(1), "one")
    );
    assertThat(s.toJavaOptional())
        .isNotPresent();

    Option<String> s2 = Match(i).option(
        Case($(1), "one"),
        Case($(2), "two")
    );
    assertThat(s2.toJavaOptional())
        .isPresent()
        .get()
        .isEqualTo("two");
  }

}
