package com.example.java.playground.features;

import com.example.java.playground.AbstractTest;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.function.UnaryOperator;
import org.junit.jupiter.api.Test;

public class FunctionalInterfaceTest extends AbstractTest {

  // Enforces Single Abstract Method
  @FunctionalInterface
  interface SamInterface {

    void foo(String value1, String value2);
  }

  interface SamInterface2 {

    void foo(String value1, String value2);
  }

  @Test
  void test() {
    SamInterface foo1 = (value1, value2) -> {
      log.info("Foo1: {} | {}", value1, value2);
    };
    SamInterface2 foo2 = (value1, value2) -> {
      log.info("Foo1: {} | {}", value1, value2);
    };

    foo1.foo("1", "2");
    foo2.foo("3", "4");
  }

  @Test
  void test_existing_interfaces() {
    Consumer<String> consumer = s -> System.out.println(s);
    DoubleConsumer doubleConsumer = value -> System.out.println(value);

    Supplier<String> supplier = () -> "42";
    DoubleSupplier doubleSupplier = () -> 42d;

    Predicate<String> predicate = s -> s.equals("42");
    DoublePredicate doublePredicate = value -> value == 42d;

    Function<Object, String> func1 = o -> o.toString();
    UnaryOperator<String> func2 = s -> "value: %s".formatted(s);
    DoubleUnaryOperator func3 = operand -> 42 + operand;

    ToIntFunction<String> func4 = value -> value.hashCode();
  }
}
