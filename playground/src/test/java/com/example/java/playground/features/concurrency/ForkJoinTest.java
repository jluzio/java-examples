package com.example.java.playground.features.concurrency;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class ForkJoinTest {

  @Data
  class IncrementTask extends RecursiveAction {

    public static final int THRESHOLD = 3;
    final long[] array;
    final int lo, hi;

    protected void compute() {
      if (hi - lo < THRESHOLD) {
        for (int i = lo; i < hi; ++i) {
          array[i]++;
        }
      } else {
        int mid = (lo + hi) >>> 1;
        invokeAll(
            new IncrementTask(array, lo, mid),
            new IncrementTask(array, mid, hi));
      }
    }
  }

  @Test
  void test() {
    ForkJoinPool pool = new ForkJoinPool();
    long[] array = LongStream.range(0, 100).toArray();
    pool.execute(new IncrementTask(array, 0, array.length));
    log.debug("array: {}", array);
  }

}
