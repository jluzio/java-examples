package com.example.java.playground.features.collection;

import static java.util.stream.Collectors.joining;
import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class StreamReduceTest {

  record User(String name, int age, String department) {

  }

  @Test
  void test_reduce() {
    var values = IntStream.range(0, 5).boxed().toList();

    var sum = values.stream().reduce(0, (acc, value) -> acc + value);
    log.info("reduce[sum]: {}", sum);

    var max = values.stream().reduce(Integer::max);
    log.info("reduce[max]: {}", max);

    var strJoin = values.stream().map(v -> v.toString())
        .reduce("", (acc, value) -> "%s|%s".formatted(acc, value));
    log.info("reduce[strJoin]: {}", strJoin);

    List<User> users = List.of(
        new User("John", 30, null),
        new User("Julie", 35, null)
    );
    int computedAges =
        users.stream().reduce(0, (acc, user) -> acc + user.age(), Integer::sum);
    log.info("reduce[computedAges]: {}", computedAges);
    int pComputedAges =
        users.parallelStream().reduce(0, (acc, user) -> acc + user.age(), Integer::sum);
    log.info("reduce[pComputedAges]: {}", pComputedAges);
    int mComputedAges =
        users.stream().map(User::age).reduce(0, (acc, age) -> acc + age);
    log.info("reduce[mComputedAges]: {}", mComputedAges);

    List<String> l = Lists.newArrayList("one", "two");
    Stream<String> sl = l.stream();
    l.add("three");
    log.info("join: {}", sl.collect(joining(" ")));

    var concatList = IntStream.range(1, 11).boxed().toList();
    List<String> concatToStrings = concatList.parallelStream().map(Object::toString)
        .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    log.info("concatToStrings: {}", concatToStrings);

    BiConsumer<ArrayList<String>, String> c = ArrayList::add;

    List<String> concatToStrings2 = concatList.parallelStream().map(Object::toString)
        .collect(
            () -> new ArrayList(),
            (acc, value) -> acc.add(value),
            (acc, otherAcc) -> acc.addAll(otherAcc));
    log.info("concatToStrings2: {}", concatToStrings2);
  }

  @Test
  void test_reduce_combiner() {
    List<User> users = List.of(
        new User("John", 30, null),
        new User("Julie", 35, null)
    );
    var joiner = Joiner.on("|").skipNulls();
    String usersToString = users.stream().reduce(
        null,
        (acc, user) -> joiner.join(acc, "{name:%s, age:%s}".formatted(user.name(), user.age())),
        (accParallel1, accParallel2) -> accParallel1
    );
    assertThat(usersToString)
        .isEqualTo("{name:John, age:30}|{name:Julie, age:35}");

    String usersToStringNoCombiner = users.stream()
        .map(user -> "{name:%s, age:%s}".formatted(user.name(), user.age()))
        .reduce(
            null,
            (acc, userToString) -> joiner.join(acc, userToString)
        );
    assertThat(usersToStringNoCombiner)
        .isEqualTo("{name:John, age:30}|{name:Julie, age:35}");
  }

}
