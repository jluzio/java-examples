package com.example.java.playground.lib.gatherers4j;

import static org.assertj.core.api.Assertions.assertThat;

import com.ginsberg.gatherers4j.Gatherers4j;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class Gatherers4jTest {

  @Test
  void distinctBy() {
    record Person(String firstName, String lastName) {

    }
    Person person1 = new Person("Todd", "Ginsberg");
    Person person2 = new Person("Emma", "Ginsberg");
    Person person3 = new Person("Todd", "Smith");

    var personList = Stream.of(person1, person2, person3)
        .gather(Gatherers4j.distinctBy(Person::firstName))
        .toList();

    assertThat(personList)
        .isEqualTo(List.of(person1, person2));
  }

  @Test
  void reverse() {
    var values = Stream
        .of("A", "B", "C")
        .gather(Gatherers4j.reverse())
        .toList();

    assertThat(values)
        .isEqualTo(List.of("C", "B", "A"));
  }
  @Test
  void repeat() {
    var values = Stream
        .of("A", "B", "C")
        .gather(Gatherers4j.repeat(3))
        .toList();

    assertThat(values)
        .isEqualTo(List.of("A", "B", "C", "A", "B", "C", "A", "B", "C"));
  }

}
