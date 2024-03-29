package com.example.wiremock.playground.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest
@EnableWireMock({
    @ConfigureWireMock(name = "default", stubLocation = "test-specific-mappings/test1")
})
@Slf4j
class WireMockStubConfig1Test {

  @Configuration
  static class Config {

  }

  @InjectWireMock("default")
  WireMockServer wiremock;

  @Test
  void test() {
    WebClient webClient = WebClient.builder()
        .baseUrl(wiremock.baseUrl())
        .build();

    String messageSimpleResponse = webClient.get().uri("/message/custom")
        .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class))
        .block();
    log.info(messageSimpleResponse);
    assertThat(messageSimpleResponse).isEqualTo("Hello world - Test1!");
  }

}
