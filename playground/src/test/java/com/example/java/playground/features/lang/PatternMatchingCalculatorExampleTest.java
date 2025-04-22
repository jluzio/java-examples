package com.example.java.playground.features.lang;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.java.playground.features.lang.PatternMatchingCalculatorExampleTest.Expr.ConstExpr;
import com.example.java.playground.features.lang.PatternMatchingCalculatorExampleTest.Expr.NegExpr;
import com.example.java.playground.features.lang.PatternMatchingCalculatorExampleTest.Expr.ProdExpr;
import com.example.java.playground.features.lang.PatternMatchingCalculatorExampleTest.Expr.SumExpr;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class PatternMatchingCalculatorExampleTest {

  sealed interface Expr {

    // @formatter:off
    record SumExpr(Expr x, Expr y) implements Expr {}
    record ProdExpr(Expr x, Expr y) implements Expr {}
    record NegExpr(Expr x) implements Expr {}
    record ConstExpr(int v) implements Expr {}
    // @formatter:on

    static int eval(Expr expr) {
      return switch (expr) {
        case ConstExpr(var v) -> v;
        case NegExpr(var v) -> -eval(v);
        case SumExpr(var x, var y) -> eval(x) + eval(y);
        case ProdExpr(var x, var y) -> eval(x) * eval(y);
      };
    }
  }

  @Test
  void test_basic_patterns() {
    var value = Expr.eval(new NegExpr(new ProdExpr(new ConstExpr(2), new ConstExpr(3))));
    log.info("-(x * y) = {}", value);
    assertThat(value)
        .isEqualTo(-6);
  }

  // @formatter:off
  record Point(double x, double y) {}
  record Circle(Point center, double radius) {}
  // @formatter:off

  @Test
  void test_nested_patterns() {
    var shape = new Circle(new Point(1, 2), 3);
    var description = switch (shape) {
      case Circle(Point(var x, var y), var r) -> "shape: circle(x=%s, y=%s, r=%s)".formatted(x, y, r);
      default -> "unknown shape";
    };
    log.info(description);
    assertThat(description)
        .containsIgnoringCase("circle");
  }

}
