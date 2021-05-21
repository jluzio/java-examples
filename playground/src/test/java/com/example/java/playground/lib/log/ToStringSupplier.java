package com.example.java.playground.lib.log;

import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ToStringSupplier<T> implements Supplier<T> {

  private final Supplier<T> supplier;

  public static <T> ToStringSupplier<T> asString(Supplier<T> supplier) {
    return new ToStringSupplier<>(supplier);
  }

  @Override
  public T get() {
    return supplier.get();
  }

  @Override
  public String toString() {
    return String.valueOf(get());
  }
}
