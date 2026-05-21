package com.example.java.playground.features.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HexFormat;
import org.junit.jupiter.api.Test;

class HexFormatTest {

  @Test
  void test() {
    HexFormat hex = HexFormat.of();
    HexFormat hexUpperCase = HexFormat.of().withUpperCase();
    byte b = 127;

    String byteStr = hex.toHexDigits(b);
    IO.println(byteStr);

    byte byteVal = (byte) HexFormat.fromHexDigits(byteStr);
    assertEquals("7f", byteStr);
    assertEquals(b, byteVal);

    var byteStrUpperCase = hexUpperCase.toHexDigits(b);
    IO.println(byteStrUpperCase);
    assertEquals("7F", byteStrUpperCase);
  }
}
