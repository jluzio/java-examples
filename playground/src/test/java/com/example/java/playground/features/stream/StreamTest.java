package com.example.java.playground.features.stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
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
class StreamTest {

  record UserData(String id, String username, String fullName) {

  }

  @Test
  void test_find() {
//        Supplier<Stream<Integer>> values = () -> Stream.of(1, 2, 3, 4, 5);
    Supplier<Stream<Integer>> values = () -> List.of(1, 2, 3, 4, 5).stream();

    log.info("findAny: {}", values.get().findAny());
    log.info("findFirst: {}", values.get().findFirst());
    log.info("findFirst empty: {}", Stream.empty().findFirst().isPresent());

    log.info("filter: {}", values.get().filter(v -> v > 2).collect(Collectors.toList()));

    assertThat(values.get().allMatch(v -> v > 2)).isFalse();
    assertThat(values.get().anyMatch(v -> v > 2)).isTrue();
    assertThat(values.get().noneMatch(v -> v > 2)).isFalse();

    Supplier<Stream<Integer>> emptyIntSupplier = Stream::empty;
    assertThat(emptyIntSupplier.get().allMatch(v -> v > 2)).isTrue();
    assertThat(emptyIntSupplier.get().anyMatch(v -> v > 2)).isFalse();
    assertThat(emptyIntSupplier.get().noneMatch(v -> v > 2)).isTrue();

    Supplier<Stream<Integer>> emptyIntStreamSupplier = Stream::empty;
    assertThat(emptyIntStreamSupplier.get().allMatch(v -> v > 2))
        .isTrue();
    assertThat(emptyIntStreamSupplier.get().anyMatch(v -> v > 2))
        .isFalse();
    assertThat(emptyIntStreamSupplier.get().noneMatch(v -> v > 2))
        .isTrue();
  }

  @Test
  void test_nullable_map() {
    var user1 = new UserData("1", "user1", "user 1");
    var user2 = new UserData("2", "user2", "user 2");
    var user3 = new UserData("3", "user3", null);

    List<UserData> users = List.of(user1, user2, user3);
    assertThatThrownBy(() ->
        users.stream()
            .map(UserData::fullName)
            .allMatch(Predicate.not(String::isBlank))
    ).isInstanceOf(NullPointerException.class);
  }

  @Test
  void test_create_stream() {
    IntStream as1 = Arrays.stream(new int[]{1, 2, 3});
    DoubleStream as2 = Arrays.stream(new double[]{1, 2, 3});
    Stream<Double> as3 = Arrays.stream(new Double[]{1d, 2d, 3d});

    var random = new Random();
    log.info("randoms: {}", random.ints().limit(3).boxed().collect(Collectors.toList()));
  }

  @Test
  void test_iterate() {
    //Stream.iterate(initial value, next value)
    Stream.iterate(0, n -> n + 1)
        .limit(10)
        .forEach(x -> log.info("iterate[1]: {}", x));

    // odd
    Stream.iterate(0, n -> n + 1)
        .filter(x -> x % 2 != 0) //odd
        .limit(10)
        .forEach(x -> log.info("iterate[1]: {}", x));

    // Fibonacci
    Stream.iterate(new int[]{0, 1}, n -> new int[]{n[1], n[0] + n[1]})
        .limit(20)
        .map(n -> n[0])
        .forEach(x -> log.info("iterate[1]: {}", x));

    Stream.iterate(1, n -> n < 5, n -> n + 1)
        .forEach(x -> log.info("iterate[1]: {}", x));
  }

  @Test
  void test_forEach() {
    var output = new ArrayList<String>();
    var values = List.of("a", "b", "c");
    IntStream.range(0, values.size())
        .forEach(i -> output.add("%s:%s".formatted(i, values.get(i))));
    log.info("output: {}", output);
    assertThat(output).contains("0:a", "1:b", "2:c");
  }

  @Test
  void test_collectors() {
    var intSum = IntStream.of(1, 2, 3)
        .filter(v -> v >= 2)
        .sum();
    log.info("intSum: {}", intSum);

    var intSum2 = Stream.of(1, 2, 3)
        .filter(v -> v >= 2)
        .collect(Collectors.summarizingInt(v -> v));
    log.info("intSum2: {}", intSum2);
  }

  @Test
  void test_cast() {
    List<Object> objectValues = List.of("v1", "v2");
    List<String> stringValues = objectValues.stream()
        .map(String.class::cast)
        .toList();
  }

  @Data
  @RequiredArgsConstructor
  @AllArgsConstructor
  class User {

    @NonNull
    private String name;
    final private int age;
    private String department;
  }

  @Test
  void test_math_collectors() {
    var users = List.of(new User("u1", 12), new User("u2", 23), new User("u3", 34));

    var ageSum = users.stream()
        .collect(Collectors.summingInt(User::getAge));
    log.info("ageSum: {}", ageSum);

    var ageAvg = users.stream()
        .collect(Collectors.averagingInt(User::getAge));
    log.info("ageAvg: {}", ageAvg);

    var ageMax = users.stream()
        .mapToInt(User::getAge)
        .max();
    log.info("ageMax: {}", ageMax);

    var ageMax2 = users.stream()
        .map(User::getAge)
        .collect(Collectors.maxBy(Integer::compare));
    log.info("ageMax2: {}", ageMax2);
  }

  @Test
  void test_groupBy() {
    String departmentSales = "sales";
    String departmentHr = "hr";
    var users = List.of(
        new User("u1", 12, departmentSales),
        new User("u2", 23, departmentSales),
        new User("u3", 34, departmentHr)
    );
    var usersByDepartment = users.stream()
        .collect(Collectors.groupingBy(User::getDepartment));
    log.info("usersByDepartment: {}", usersByDepartment);
  }

  @Test
  void test_teeing_collectors() {
    double mean = Stream.of(1, 2, 3, 4, 5)
        .collect(Collectors.teeing(
            Collectors.summingDouble(i -> i),
            Collectors.counting(),
            (sum, n) -> sum / n));

    log.info("mean: {}", mean);
  }

  @Data
  @Builder
  static class OptionalStreamData {

    private List<User> users;
  }

  @Test
  void test_optional_stream() {
    var optData = Optional.ofNullable(
        OptionalStreamData.builder()
            .users(
                List.of(
                    new User("name1", 11),
                    new User("name2", 22)

                )
            )
            .build());

    var user1Name = optData.stream()
        .map(OptionalStreamData::getUsers)
        .flatMap(List::stream)
        .findFirst()
        .map(User::getName);
    log.info("{}", user1Name);

    var user1NameJava8 = optData.map(Stream::of).orElse(Stream.empty())
        .map(OptionalStreamData::getUsers)
        .flatMap(List::stream)
        .findFirst()
        .map(User::getName);
    log.info("{}", user1NameJava8);
  }

  @Test
  void find_first_nullable() {
    HashMap<String, String> map = new HashMap<>();
    map.put("key1", null);
    map.put("key2", "val2");

    assertThat(
        Stream.of("key1", "key2")
            .flatMap(k -> Stream.ofNullable(map.get(k)))
            .findFirst()
            .orElse("not-found"))
        .isEqualTo("val2");

    assertThat(
        Stream.of("key1", "key3")
            .flatMap(k -> Stream.ofNullable(map.get(k)))
            .findFirst()
            .orElse("not-found"))
        .isEqualTo("not-found");
  }


  @Test
  void collectMap() {
    var users = IntStream.rangeClosed(1, 3)
        .mapToObj(it ->
            new UserData("id%s".formatted(it), "username%s".formatted(it), "fname"))
        .toList();

    var usernameByIdMap = users.stream()
        .collect(Collectors.toMap(UserData::id, UserData::username));
    assertThat(usernameByIdMap)
        .isEqualTo(Map.of(
            "id1", "username1",
            "id2", "username2",
            "id3", "username3"
        ));
  }

}
