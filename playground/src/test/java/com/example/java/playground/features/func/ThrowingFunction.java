package com.example.java.playground.features.func;

import java.util.function.Function;

@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Throwable> extends Function<T, R> {

  R throwingApply(T t) throws E;

  @Override
  default R apply(T t) {
    try {
      return throwingApply(t);
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }

  static <T, R, E extends Throwable> Function<T, R> unchecked(
      ThrowingFunction<T, R, E> throwingFunction) {
    return throwingFunction;
  }
}
