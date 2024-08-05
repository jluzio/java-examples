package com.example.java.playground.features.stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

@Log4j2
class CollectorsTest {

  @Test
  void toMap_unique_keys() {
    var values = List.of(
        Pair.of("1", "1.1"),
        Pair.of("2", "2.1"),
        Pair.of("3", "3.1")
    );
    var valuesMap = values.stream().collect(Collectors.toMap(
        Pair::getKey,
        Pair::getValue
    ));
    log.info("{}", valuesMap);
    log.info("{}", valuesMap.getClass());
    log.info("{}", valuesMap.values());
    assertThat(valuesMap)
        .isEqualTo(Map.of(
            "1", "1.1",
            "2", "2.1",
            "3", "3.1"
        ));
  }

  @Test
  void toMap_duplicate_keys() {
    var values = List.of(
        Pair.of("1", "1.1"),
        Pair.of("1", "1.2"),
        Pair.of("2", "2.1"),
        Pair.of("3", "3.1"),
        Pair.of("2", "2.2"),
        Pair.of("1", "1.3"),
        Pair.of("2", "2.3")
    );
    assertThatThrownBy(() -> values.stream().collect(Collectors.toMap(
        Pair::getKey,
        Pair::getValue
    )))
        .isInstanceOf(IllegalStateException.class);

    var valuesMap = values.stream().collect(Collectors.toMap(
        Pair::getKey,
        Pair::getValue,
        (pair1, pair2) -> pair1
    ));
    log.info("{}", valuesMap);
    log.info("{}", valuesMap.getClass());
    log.info("{}", valuesMap.values());
    assertThat(valuesMap)
        .isEqualTo(Map.of(
            "1", "1.1",
            "2", "2.1",
            "3", "3.1"
        ));
  }

  @Test
  void groupBy_aggregate_functions() {
    List<String> values = List.of("a1234", "a123", "a12345", "b1", "b123");

    Map<String, Long> collectCount = values.stream()
        .collect(Collectors.groupingBy(
            v -> v.substring(0, 1),
            Collectors.counting()
        ));
    assertThat(collectCount)
        .isEqualTo(Map.of(
            "a", 3L,
            "b", 2L));

    Map<String, Double> collectAverage = values.stream()
        .collect(Collectors.groupingBy(
            v -> v.substring(0, 1),
            Collectors.averagingInt(v -> v.length() - 1)
        ));
    assertThat(collectAverage)
        .isEqualTo(Map.of(
            "a", 4d,
            "b", 2d));
  }

  @Test
  void groupBy_list() {
    List<String> values = List.of("a1234", "a123", "a12345", "b1", "b123");
    Map<Object, List<String>> collect = values.stream()
        .collect(Collectors.groupingBy(
            v -> v.substring(0, 1)
        ));
    assertThat(collect)
        .isEqualTo(Map.of(
            "a", List.of("a1234", "a123", "a12345"),
            "b", List.of("b1", "b123")));
  }

  @Test
  void joining() {
    var employees = employees();

    var employeeNamesSortedByName = employees.stream()
        .map(Employee::name)
        .sorted()
        .collect(Collectors.joining(","));
    log.info(employeeNamesSortedByName);
    assertThat(employeeNamesSortedByName)
        .isEqualTo("Ana,Ben,John,Mary,Peter,Reggie,Sam,Samantha,Ted,Valery");
  }

  @Test
  void groupBy_counting() {
    var employees = employees();

    var employeeCountGroupedByDepartment = employees.stream()
        .collect(Collectors.groupingBy(Employee::department, Collectors.counting()));
    log.info(employeeCountGroupedByDepartment);
    assertThat(employeeCountGroupedByDepartment)
        .isEqualTo(Map.of(
            "DM", 2L,
            "HR", 2L,
            "IT", 6L
        ));
  }

  @Test
  void toMap_customMap() {
    var employees = employees();

    Comparator<Employee> employeeComparator = Comparator.comparingInt(Employee::id);
    var employeeByIdMap = employees.stream()
        .collect(Collectors.toMap(
            employee -> employee,
            Employee::id,
            (v1, v2) -> v1,
            () -> new TreeMap<>(employeeComparator)
        ));
    log.info(employeeByIdMap);
    log.info(employeeByIdMap.getClass().getName());

    assertThat(employeeByIdMap)
        .isInstanceOf(TreeMap.class);
  }

  record Employee(int id, String name, int age, String department, int salary) {

  }

  private List<Employee> employees() {
    List<Employee> employees = new ArrayList<>();
    employees.add(new Employee(101, "Ana", 38, "IT", 50000));
    employees.add(new Employee(102, "Ted", 40, "DM", 40000));
    employees.add(new Employee(103, "Peter", 35, "HR", 70000));
    employees.add(new Employee(104, "John", 38, "IT", 50000));
    employees.add(new Employee(105, "Sam", 38, "HR", 30000));
    employees.add(new Employee(106, "Reggie", 32, "DM", 30000));
    employees.add(new Employee(107, "Samantha", 34, "IT", 50000));
    employees.add(new Employee(108, "Mary", 34, "IT", 80000));
    employees.add(new Employee(109, "Ben", 37, "IT", 60000));
    employees.add(new Employee(110, "Valery", 34, "IT", 70000));
    return employees;
  }

}
