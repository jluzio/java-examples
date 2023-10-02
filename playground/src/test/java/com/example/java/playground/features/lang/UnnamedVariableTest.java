package com.example.java.playground.features.lang;

import org.junit.jupiter.api.Test;

public class UnnamedVariableTest {

  // TODO: 02/10/2023 Use unit test when it is supported by running in the IDE
//  @Test
  void test() {
    String s = "test";
    try {
      int i = Integer.parseInt(s);
      //use i
    } catch (NumberFormatException _) {
      System.out.println("Invalid number: " + s);
    }
  }
}
