package com.example.java.playground.lib.jspecify;

import org.jspecify.annotations.NullUnmarked;

public class JSpecifyTestHelper {

  /**
   * Making method return unspecified null, so compiler with NullAway checks allows it
   */
  @NullUnmarked
  public static String unspecifiedNull() {
    return null;
  }
}
