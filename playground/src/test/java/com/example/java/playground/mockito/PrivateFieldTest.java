package com.example.java.playground.mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PrivateFieldTest {

  @InjectMocks
  Foo foo;

  @Mock
  Bar bar;

  public static class Foo {

    private Bar bar = new Bar();

    public String bar() {
      return bar.print();
    }
  }

  public static class Bar {

    public String print() {
      return "bar";
    }
  }

  @Test
  public void test() {
    doReturn("mocked-bar").when(bar).print();
    assertEquals("mocked-bar", foo.bar());
  }

}
