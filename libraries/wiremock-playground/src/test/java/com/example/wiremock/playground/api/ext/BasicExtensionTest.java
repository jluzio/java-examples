package com.example.wiremock.playground.api.ext;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.wiremock.spring.EnableWireMock;

@SpringBootTest
@EnableWireMock
class BasicExtensionTest {

  @Configuration
  static class AppConfiguration {

  }

  @Value("${wiremock.server.baseUrl}")
  private String wireMockUrl;

  @Test
  void returns_a_ping() {
    stubFor(get("/ping")
        .willReturn(ok("pong")));

    RestClient client = RestClient.create();
    String body = client.get()
        .uri(wireMockUrl + "/ping")
        .retrieve()
        .body(String.class);

    assertThat(body)
        .isEqualTo("pong");
  }

}
