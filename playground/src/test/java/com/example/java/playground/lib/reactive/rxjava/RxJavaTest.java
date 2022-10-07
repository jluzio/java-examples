package com.example.java.playground.lib.reactive.rxjava;

import static org.junit.jupiter.api.Assertions.assertEquals;

import hu.akarnokd.rxjava3.math.MathObservable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observables.ConnectableObservable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class RxJavaTest {

  @Test
  void simple() {
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

  @Test
  void aggregate() {
    Observable.range(1, 10)
        .to(MathObservable::max)
        .map(Objects::toString)
        .subscribe(log::info);

    Observable.range(1, 10)
        .collect(Collectors.maxBy(Integer::compareTo))
        .mapOptional(v -> v)
        .map(Objects::toString)
        .subscribe(log::info);

    Observable.range(1, 10)
        .to(MathObservable::averageFloat)
        .map(Objects::toString)
        .subscribe(log::info);

    Observable.range(1, 10)
        .collect(Collectors.averagingInt(Integer::intValue))
        .map(Objects::toString)
        .subscribe((Consumer<String>) log::info);
  }

  @Test
  void timer() throws InterruptedException {
    Observable.interval(20, TimeUnit.MILLISECONDS)
        .take(3)
        .map(Objects::toString)
        .toList()
        .blockingSubscribe(v -> log.info("{}", v));
  }

  @Test
  void simple_background_computation() throws InterruptedException {
    Flowable.fromCallable(() -> {
          Thread.sleep(20); //  imitate expensive computation
          return "Done";
        })
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.single())
        .subscribe(System.out::println, Throwable::printStackTrace);

    Thread.sleep(50); // <--- wait for the flow to finish
  }

  @Test
  void concurrency_within_a_flow() {
    Flowable.range(1, 10)
        .observeOn(Schedulers.computation())
        .map(v -> v * v)
        .blockingSubscribe(System.out::println);
  }

  @Test
  void parallel_processing() {
    int rangeStart = 1;
    int rangeCount = 10;

    Function<Integer, Integer> defaultConsumer = v -> v * v;
    Function<Integer, Integer> delayedConsumer = v -> {
      try {
        Thread.sleep(10 - v % 7);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return v * v;
    };
    Consumer<String> consumer = System.out::println;
//    Consumer<String> consumer = log::info;

    Scheduler scheduler = Schedulers.computation();

    log.info("default");

    Flowable.range(rangeStart, rangeCount)
        .map(delayedConsumer)
        .map(Objects::toString)
        .blockingSubscribe(consumer);

    log.info("observe parallel");
    Flowable.range(rangeStart, rangeCount)
        .observeOn(scheduler)
        .map(delayedConsumer)
        .map(Objects::toString)
        .blockingSubscribe(consumer);

    log.info("parallel basic syntax");
    Flowable.range(rangeStart, rangeCount)
        .flatMap(v ->
            Flowable.just(v)
                .subscribeOn(scheduler)
                .map(delayedConsumer)
        )
        .map(Objects::toString)
        .blockingSubscribe(consumer);

    log.info("parallel");
    Flowable.range(rangeStart, rangeCount)
        .parallel()
        .runOn(scheduler)
        .map(delayedConsumer)
        .map(Objects::toString)
        .sequential()
        .blockingSubscribe(consumer);
  }

  @Test
  void deferred_dependant() {
    log.info("not deferred");
    AtomicInteger count1 = new AtomicInteger();
    Observable.range(1, 10)
        .doOnNext(ignored -> count1.incrementAndGet())
        .ignoreElements()
        .andThen(Single.just(count1.get()))
        .subscribe(System.out::println);

    log.info("deferred");
    AtomicInteger count2 = new AtomicInteger();
    Observable.range(1, 10)
        .doOnNext(ignored -> count2.incrementAndGet())
        .ignoreElements()
        .andThen(Single.defer(() -> Single.just(count2.get())))
        .subscribe(System.out::println);

    log.info("deferred alternative");
    AtomicInteger count3 = new AtomicInteger();
    Observable.range(1, 10)
        .doOnNext(ignored -> count3.incrementAndGet())
        .ignoreElements()
        .andThen(Single.fromCallable(() -> count3.get()))
        .subscribe(System.out::println);
  }

  @Test
  void scan() {
    String[] letters = {"a", "b", "c"};
    AtomicReference<String> result = new AtomicReference<>("");
    Consumer<String> consumer = value -> result.accumulateAndGet(value, String::concat);

    result.set("");
    Observable.fromArray(letters)
        .scan(new StringBuilder(), StringBuilder::append)
        .map(Objects::toString)
        .subscribe(consumer);
    assertEquals("aababc", result.get());
  }

  @Test
  void reducers() {
    String[] letters = {"a", "b", "c"};
    AtomicReference<String> result = new AtomicReference<>("");
    Consumer<String> consumer = value -> result.accumulateAndGet(value, String::concat);

    result.set("");
    Observable.fromArray(letters)
        .reduce(new StringBuilder(), StringBuilder::append)
        .map(Objects::toString)
        .subscribe(consumer);
    assertEquals("abc", result.get());

    result.set("");
    Observable.fromArray(letters)
        .collect(Collectors.joining())
        .subscribe(consumer);
    assertEquals("abc", result.get());
  }

  @Test
  void groupBy() {
    Map<String, List<Integer>> valuesMap = new HashMap<>();

    Observable.range(0, 11)
        .groupBy(i -> 0 == (i % 2) ? "EVEN" : "ODD")
        .subscribe(group ->
            group
                .toList()
                .subscribe(values -> valuesMap.put(group.getKey(), values)));

    assertEquals(valuesMap.get("EVEN"), List.of(0, 2, 4, 6, 8, 10));
    assertEquals(valuesMap.get("ODD"), List.of(1, 3, 5, 7, 9));
  }

  @Test
  void filter() {
    AtomicReference<List<Integer>> values = new AtomicReference<>();

    Observable.range(0, 11)
        .filter(i -> i % 2 == 1)
        .toList()
        .subscribe(values::set);

    assertEquals(values.get(), List.of(1, 3, 5, 7, 9));
  }

  @Test
  void mapOptional() {
    AtomicReference<List<String>> values = new AtomicReference<>();
    Map<Integer, String> relatedValuesMap = Map.of(
        1, "one",
        4, "four",
        10, "ten"
    );

    Observable.range(1, 10)
        .mapOptional(v -> Optional.ofNullable(relatedValuesMap.get(v)))
        .toList()
        .subscribe(values::set);

    log.info("values: {}", values);
  }

  @Test
  void single() {
    AtomicReference<Integer> value = new AtomicReference<>();

    Observable.fromOptional(Optional.<Integer>empty())
        .single(10)
        .subscribe(value::set);
    assertEquals(10, value.get());

    Observable.fromOptional(Optional.<Integer>empty())
        .defaultIfEmpty(5)
        .single(10)
        .subscribe(value::set);
    assertEquals(5, value.get());
  }

  @Test
  void subject() {
    List<Integer> values1 = new ArrayList<>();
    List<Integer> values2 = new ArrayList<>();

    PublishSubject<Integer> subject = PublishSubject.create();
    subject.subscribe(values1::add);
    subject.onNext(1);
    subject.onNext(2);
    subject.onNext(3);
    subject.subscribe(values2::add);
    subject.onNext(4);
    subject.onComplete();

    assertEquals(List.of(1, 2, 3, 4), values1);
    assertEquals(List.of(4), values2);
  }

  @Test
  void using_aka_resource_management() {
    Observable.using(
            () -> new Scanner(new StringReader("12 23 34 45 56")),
            scanner -> Observable.create(o -> {
                  while (scanner.hasNextInt()) {
                    o.onNext(scanner.nextInt());
                  }
                  o.onComplete();
                }
            ),
            scanner -> scanner.close()
        )
        .map(Object::toString)
        .subscribe(log::info);
  }

  @Test
  void connectable_observable() throws InterruptedException {
    ConnectableObservable<Long> connectableObservable = Observable
        .interval(10, TimeUnit.MILLISECONDS)
        .take(3)
        .publish();

    connectableObservable
        .map(Objects::toString)
        .subscribe(log::info);

    Thread.sleep(15);
    log.info("Still no events, starting now");
    connectableObservable
        .connect();

    Thread.sleep(25);
  }

}
