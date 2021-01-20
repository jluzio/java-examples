package com.example.java.playground.reactive.reactor;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class ReactorTest {

  @Test
  void simpleTest() {
    List<String> names = List.of("Joe", "Bart", "Henry", "Nicole", "ABSLAJNFOAJNFOANFANSF");
    List<Integer> stats = List.of(103, 104, 105, 106, 121);

    Flux<String> ids = Flux
        .interval(Duration.ofMillis(50))
        .take(names.size())
        .map(Object::toString);

    Function<String, Mono<String>> ifhrName = id -> Mono
        .delay(Duration.ofMillis(10))
        .map((ignored) -> names.get(Integer.valueOf(id)));
    Function<String, Mono<Integer>> ifhrStat = id -> Mono
        .delay(Duration.ofMillis(10))
        .map((ignored) -> stats.get(Integer.valueOf(id)));

    Flux<String> combinations =
        ids.flatMap(id -> {
          Mono<String> nameTask = ifhrName.apply(id);
          Mono<Integer> statTask = ifhrStat.apply(id);

          return nameTask.zipWith(statTask,
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
