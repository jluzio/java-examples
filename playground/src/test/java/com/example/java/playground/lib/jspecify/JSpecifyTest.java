package com.example.java.playground.lib.jspecify;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.NullUnmarked;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;

class JSpecifyTest {

  static class MethodAnnotatedExample {

    @Nullable
    static String emptyToNull(@NonNull String x) {
      return x.isEmpty() ? null : x;
    }

    @NonNull
    static String nullToEmpty(@Nullable String x) {
      return x == null ? "" : x;
    }
  }


  @NullMarked
  static class NullMarkedMethodAnnotatedExample {

    @Nullable
    static String emptyToNull(String x) {
      return x.isEmpty() ? null : x;
    }

    static String nullToEmpty(@Nullable String x) {
      return x == null ? "" : x;
    }

    // NullUnmarked disables NullMarked usage, so it becomes unspecified nullability again
    @NullUnmarked
    @Nullable
    static String nullUnmarkedEmptyToNull(@NonNull String x) {
      return x.isEmpty() ? null : x;
    }

  }

  /**
   * Note: can be used with NullAway for compile time checks
   * @see <a href="https://spring.io/blog/2025/03/10/null-safety-in-spring-apps-with-jspecify-and-null-away">null-safety-in-spring-apps-with-jspecify-and-null-away</a>
   * @see <a href="https://github.com/uber/NullAway">NullAway</a>
   * @see <a href="https://github.com/sdeleuze/jspecify-nullway-demo">jspecify-nullway-demo</a>
   */
  @Test
  void test() {
    assertThatThrownBy(() -> MethodAnnotatedExample.emptyToNull(null))
        .isInstanceOf(NullPointerException.class);
  }

}
