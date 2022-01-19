package com.example.wiremock.playground.api;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Getter
public class GreetingWebClient {

  private final WebClient client = WebClient.create("http://localhost:8080");
  private final Mono<String> helloWorld = client
      .get()
      .uri("/hello")
      .accept(MediaType.TEXT_PLAIN)
      .retrieve()
      .bodyToMono(String.class);

  public static void main(String[] args) {
    var client = new GreetingWebClient();
    log.info("hello: {}", client.getHelloWorld().block());
  }

}
