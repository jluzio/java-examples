package com.example.java.playground.lib.commons.collections;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Predicate;
import org.apache.commons.collections4.functors.ComparatorPredicate;
import org.apache.commons.collections4.functors.ComparatorPredicate.Criterion;
import org.junit.jupiter.api.Test;

class ComparatorPredicateTest {

  @Test
  void comparatorPredicate() {
    int value = 1;
    int compareTo1 = 2;
    int compareTo2 = 1;

    var commonsPredicate1 = ComparatorPredicate.comparatorPredicate(
        value, Integer::compareTo, Criterion.LESS);
    Predicate<Integer> predicate1 = commonsPredicate1::evaluate;

    assertThat(commonsPredicate1.evaluate(compareTo1))
        .isTrue();
    assertThat(commonsPredicate1.evaluate(compareTo2))
        .isFalse();
    assertThat(predicate1.test(compareTo1))
        .isTrue();
    assertThat(predicate1.test(compareTo2))
        .isFalse();

    Predicate<Integer> predicate2 = ComparatorPredicate.comparatorPredicate(
        value, Integer::compareTo, Criterion.GREATER_OR_EQUAL)::evaluate;
    assertThat(predicate2.test(compareTo1))
        .isFalse();
    assertThat(predicate2.test(compareTo2))
        .isTrue();
  }

}
