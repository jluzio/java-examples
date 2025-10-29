package com.example.wiremock.playground.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest
@Import({GreetingRouter.class, GreetingHandler.class})
class GreetingRouterTest {

  @Autowired
  WebTestClient webTestClient;

  @Test
  void hello() {
    webTestClient
        .get().uri("/hello")
        .accept(MediaType.TEXT_PLAIN)
        .exchange()
        .expectStatus().isOk()
        .expectBody(String.class).isEqualTo("Hello world!");
    webTestClient
        .get().uri("/hello/Spring")
        .accept(MediaType.TEXT_PLAIN)
        .exchange()
        .expectStatus().isOk()
        .expectBody(String.class).isEqualTo("Hello Spring!");
  }

}
