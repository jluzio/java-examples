package com.example.java.playground.rxjava;

import com.example.java.playground.AbstractTest;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

public class RxJavaTest extends AbstractTest {

  @Test
  void test() {
    Flowable.just("Hello world")
        .subscribe(System.out::println);

    Flowable.range(1, 10)
        .subscribe(v -> log.info("[simple]value: {}", v));

    Flowable.range(1, 10)
        .observeOn(Schedulers.computation())
        .map(v -> v * v)
        .subscribe(v -> log.info("[compute]value: {}", v));
  }

  @Test
  void error_handling() {
    Observable.range(-4, 10)
        .map(i -> 100 / i)
        .subscribe(System.out::println, t -> System.out.println("some error happened"));

    Observable.range(-4, 10)
        .map(i -> 100 / i)
        .onErrorReturnItem(-1)
        .subscribe(System.out::println, t -> System.out.println("some error happened"));

  }
}
