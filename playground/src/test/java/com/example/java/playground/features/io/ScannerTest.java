package com.example.java.playground.features.io;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class ScannerTest {

  @Test
  void test() {
    var data = new ByteArrayInputStream("123 lalala 456".getBytes());
    try (var scanner = new Scanner(data)) {
      var int1 = scanner.nextInt();
      var str2 = scanner.next();
      var int3 = scanner.nextInt();
      assertThat(int1)
          .isEqualTo(123);
      assertThat(str2)
          .isEqualTo("lalala");
      assertThat(int3)
          .isEqualTo(456);
    }
  }

}
