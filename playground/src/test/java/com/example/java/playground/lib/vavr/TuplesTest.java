package com.example.java.playground.lib.vavr;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import io.vavr.control.Either;
import io.vavr.control.Try;
import java.util.NoSuchElementException;
import lombok.experimental.UtilityClass;
import org.junit.jupiter.api.Test;

class TuplesTest {

  @UtilityClass
  static class ErrorAsValues {

    // adopting convention of Error left, Success right, same as Haskell for example

    public static <E, T> Either<E, T> value(T value) {
      return Either.right(value);
    }

    public static <E, T> Either<E, T> error(E error) {
      return Either.left(error);
    }
  }

  @Test
  void tuple() {
    Tuple2<String, Integer> tuple2 = Tuple.of("text", 123)
        .map1(t -> t.concat("-value"));
    assertThat(tuple2)
        .satisfies(it -> assertThat(it.arity()).isEqualTo(2))
        .satisfies(it -> assertThat(it._1()).isEqualTo("text-value"))
        .satisfies(it -> assertThat(it._2()).isEqualTo(123))
    ;

    Tuple3<String, Integer, String> tuple3 = tuple2.append("t3");
    tuple3 = tuple3.update1("t1");
    assertThat(tuple3)
        .satisfies(it -> assertThat(it.arity()).isEqualTo(3))
        .satisfies(it -> assertThat(it._1()).isEqualTo("t1"))
        .satisfies(it -> assertThat(it._3()).isEqualTo("t3"))
    ;
  }

  @Test
  void either() {
    var either = Either.<String, Integer>right(123);
    assertThat(either.isLeft())
        .isFalse();
    assertThat(Try.of(either::getLeft).isSuccess())
        .isFalse();
    assertThat(either.isRight())
        .isTrue();
    assertThat(Try.of(either::get).isSuccess())
        .isTrue();

    Either<Throwable, String> illegalValue = ErrorAsValues.error(
        new IllegalArgumentException("illegal value"));
    assertThat(illegalValue.isLeft())
        .isTrue();

    Either<Throwable, String> successValue = ErrorAsValues.value("42");
    assertThat(successValue.isRight())
        .isTrue();

    Either<Throwable, Object> tryEitherException = Try.of(() -> {
          throw new IllegalArgumentException("no data");
        })
        .toEither();
    assertThat(tryEitherException.isLeft()).isTrue();

    Either<Throwable, String> tryEitherSuccess = Try.of(() -> "42")
        .toEither();
    assertThat(tryEitherSuccess.isRight()).isTrue();
  }

}
