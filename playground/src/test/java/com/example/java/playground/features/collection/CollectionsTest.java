package com.example.java.playground.features.collection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class CollectionsTest {

  @Test
  void enumeration_to_list() {
    var vector = new Vector<>(List.of("1", "2", "3"));
    Enumeration<String> enumeration = vector.elements();
    assertThat(Collections.list(enumeration))
        .isEqualTo(List.of("1", "2", "3"));
  }

  @Test
  void basic_queue_deque_ArrayDeque() {
    Deque<String> deque = new ArrayDeque<>();
    Queue<String> queue = deque;
    queue.add("1");
    queue.add("2");
    queue.add("3");

    assertThat(queue.element())
        .isEqualTo("1");

    deque.addFirst("0");
    assertThat(queue.element())
        .isEqualTo("0");

    assertThat(deque.pop())
        .isEqualTo("0");
    assertThat(deque.element())
        .isEqualTo("1");

    assertThat(deque.getLast())
        .isEqualTo("3");
  }

  @Test
  void basic_queue_deque_LinkedList() {
    var linkedList = new LinkedList<String>();
    Deque<String> deque = linkedList;
    List<String> list = linkedList;

    deque.offer("1");
    deque.offer("2");
    list.add("3");

    assertThat(list)
        .containsExactly("1", "2", "3");
  }

  @Test
  void queue_ArrayBlockingQueue() {
    Queue<String> queue = new ArrayBlockingQueue<>(10);
    queue.add("1");
    queue.add("2");
    queue.add("3");
    assertThat(queue.element())
        .isEqualTo("1");
  }

  @Test
  void stack() {
    var stack = new Stack<String>();
    stack.push("1");
    stack.push("2");
    assertThat(stack.pop())
        .isEqualTo("2");
  }

}
