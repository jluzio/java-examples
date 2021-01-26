package com.example.java.playground.reactive.reactor;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class FirstExampleReactorTest {

  @Test
  void firstExampleTest() {
    List<String> names = List.of("Joe", "Bart", "Henry", "Nicole", "ABSLAJNFOAJNFOANFANSF");
    List<Integer> stats = List.of(103, 104, 105, 106, 121);

    Flux<String> ids = Flux.range(0, names.size())
        .map(Object::toString);

    Function<String, Mono<String>> getName = id -> Mono.just(id)
        .map(Integer::valueOf)
        .map(names::get);
    Function<String, Mono<Integer>> getStat = id -> Mono.just(id)
        .map(Integer::valueOf)
        .map(stats::get);

    Flux<String> combinations =
        ids.flatMap(id -> {
          Mono<String> nameTask = getName.apply(id);
          Mono<Integer> statTask = getStat.apply(id);

          return nameTask.zipWith(
              statTask,
              (name, stat) -> "Name " + name + " has stats " + stat);
        });

    Mono<List<String>> result = combinations.collectList();

    List<String> results = result.block();
    assertThat(results).containsExactly(
        "Name Joe has stats 103",
        "Name Bart has stats 104",
        "Name Henry has stats 105",
        "Name Nicole has stats 106",
        "Name ABSLAJNFOAJNFOANFANSF has stats 121"
    );
  }

}
