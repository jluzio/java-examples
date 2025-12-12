package com.example.wiremock.playground.api.ext;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.wiremock.spring.EnableWireMock;

@SpringBootTest
@EnableWireMock
class HelloTemplateDefaultTest {

  @Configuration
  static class AppConfiguration {

  }

  @Value("${wiremock.server.baseUrl}")
  private String wireMockUrl;

  @Test
  void test_default_response() {
    RestClient client = RestClient.builder()
        .baseUrl(wireMockUrl)
        .build();

    String body = client.get()
        .uri("/hello-template")
        .retrieve()
        .body(String.class);
    assertThat(body)
        .isEqualTo("Hello world!");
  }

  @Test
  void test_custom_response() {
    // For reusing templates, customization of data should be done with headers if no other "normal" API parameter can be used.
    // With the header strategy, it should be added either the client instance or some kind of interceptor for easier testing.
    RestClient client = RestClient.builder()
        .baseUrl(wireMockUrl)
        .defaultHeader("X-CUSTOM-VALUE", "test")
        .build();

    String body = client.get()
        .uri("/hello-template")
        .retrieve()
        .body(String.class);
    assertThat(body)
        .isEqualTo("Hello test!");
  }

}
