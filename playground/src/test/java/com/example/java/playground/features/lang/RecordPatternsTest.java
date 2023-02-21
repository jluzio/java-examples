package com.example.java.playground.features.lang;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class RecordPatternsTest {

  record Point(int x, int y) {

  }

  record Total(Point p1, Point p2) {

  }

  @Test
  void test() {
    assertThat(sum(new Point(10, 20)))
        .isEqualTo(30);
    assertThat(sum(new Total(new Point(10, 5), new Point(2, 3))))
        .isEqualTo(20);
  }

  Number sum(Object o) {
    // record pattern
    if (o instanceof Point p) {
      int x = p.x();  // get x()
      int y = p.y();  // get y()
      return x + y;
    }
    // record nested pattern
    if (o instanceof Total(Point(int x, int y), Point(int x2, int y2))) {
      return x + y + x2 + y2;
    }
    throw new UnsupportedOperationException();
  }

}
