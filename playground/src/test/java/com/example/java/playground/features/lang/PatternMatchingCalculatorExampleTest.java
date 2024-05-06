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
    record SumExpr(Expr x, Expr y) implements Expr {};
    record ProdExpr(Expr x, Expr y) implements Expr {};
    record NegExpr(Expr x) implements Expr {};
    record ConstExpr(int v) implements Expr {};

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
  void test() {
    var value = Expr.eval(new NegExpr(new ProdExpr(new ConstExpr(2), new ConstExpr(3))));
    log.debug("-(x * y) = {}", value);
    assertThat(value)
        .isEqualTo(-6);
  }

}
