package com.example.wiremock.playground.api;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class GreetingHandler {

  public Mono<ServerResponse> helloWorld(ServerRequest request) {
    return ServerResponse
        .ok()
        .contentType(MediaType.TEXT_PLAIN)
        .body(BodyInserters.fromValue("Hello world!"));
  }

  public Mono<ServerResponse> hello(ServerRequest request) {
    String name = request.pathVariable("name");
    return ServerResponse
        .ok()
        .contentType(MediaType.TEXT_PLAIN)
        .body(BodyInserters.fromValue("Hello %s!".formatted(name)));
  }

}
