package com.example.java.playground.features.collection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.SequencedCollection;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SequencedCollectionTest {

  @Test
  void test_list() {
    SequencedCollection<Integer> collection = new ArrayList<>();

    collection.add(1); // [1]
    collection.addFirst(0);	// [0, 1]
    collection.addLast(2);		// [0, 1, 2]
    assertThat(collection).isEqualTo(List.of(0, 1, 2));

    assertThat(collection.getFirst()).isEqualTo(0);
    assertThat(collection.getLast()).isEqualTo(2);
  }

  @Test
  void test_set() {
    SequencedCollection<Integer> collection = new LinkedHashSet<>();

    collection.add(1); // [1]
    collection.addFirst(0);	// [0, 1]
    collection.addLast(2);		// [0, 1, 2]
    assertThat(collection).isEqualTo(Set.of(0, 1, 2));

    assertThat(collection.getFirst()).isEqualTo(0);
    assertThat(collection.getLast()).isEqualTo(2);
  }

}
