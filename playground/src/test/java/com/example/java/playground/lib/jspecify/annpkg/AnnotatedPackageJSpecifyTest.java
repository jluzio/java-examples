package com.example.java.playground.lib.jspecify.annpkg;

import static com.example.java.playground.lib.jspecify.JSpecifyTestHelper.unspecifiedNull;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullUnmarked;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;

class AnnotatedPackageJSpecifyTest {

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

  @Test
  void test() {
    assertThatThrownBy(() -> NullMarkedMethodAnnotatedExample.emptyToNull(unspecifiedNull()))
        .isInstanceOf(NullPointerException.class);
  }

}
