package com.example.java.playground.features.javadoc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/// https://docs.oracle.com/en/java/javase/23/javadoc/using-markdown-documentation-comments.html#GUID-42FB039A-A551-4616-8981-BAF46B5138E7
class MarkdownTest {

  class SomeClass {

    /// Some _Markdown_ doc
    ///
    /// @since 42
    /// * [#bar()] - bar method reference
    /// * a module [java.base/]
    /// * a package [java.util]
    /// * a class [String]
    /// * a field [String#CASE_INSENSITIVE_ORDER]
    /// * a method [String#chars()]
    public String foo() {
      return "foo :: %s".formatted(bar());
    }

    /// | Latin | Greek |
    /// |-------|-------|
    /// | a     | alpha |
    /// | b     | beta  |
    /// | c     | gamma |
    public String bar() {
      return "bar";
    }
  }

  @Test
  void test() {
    var bean = new SomeClass();
    Assertions.assertDoesNotThrow(() -> IO.println(bean.foo()));
  }

}
