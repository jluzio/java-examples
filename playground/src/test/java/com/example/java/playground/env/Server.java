package com.example.java.playground.env;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class Server {

  public static void main(String[] args) {
    // set environment variable in runner or OS
    // JAVA_TOOL_OPTIONS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5555
    new Server().run();
  }

  public void run() {
    Flux.interval(Duration.ofSeconds(2))
        .doOnNext(this::tick)
        .blockLast();
  }

  public void tick(Long value) {
    log.info("tick: {}", value);
  }

}
