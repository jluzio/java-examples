package com.example.java.playground.lib.validator.jsr380;

import java.time.temporal.Temporal;
import java.util.function.Function;

public class DefaultTemporalMapper implements Function<Object, Temporal> {

  public static final DefaultTemporalMapper INSTANCE = new DefaultTemporalMapper();

  @Override
  public Temporal apply(Object object) {
    if (object == null) {
      return null;
    } else if (object instanceof Temporal) {
      return (Temporal) object;
    } else {
      throw new IllegalArgumentException("Unsupported class: " + object.getClass().getName());
    }
  }

}
