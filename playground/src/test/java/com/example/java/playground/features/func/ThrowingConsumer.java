package com.example.java.playground.features.func;

import java.util.function.Consumer;

@FunctionalInterface
public interface ThrowingConsumer<T, E extends Throwable> extends Consumer<T> {

  void throwingAccept(T t) throws E;

  @Override
  default void accept(T t) {
    try {
      throwingAccept(t);
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }

  static <T, E extends Throwable> Consumer<T> unchecked(ThrowingConsumer<T, E> throwingConsumer) {
    return throwingConsumer;
  }
}
