package com.example.wiremock.playground.api.ext;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import org.wiremock.spring.InjectWireMock;

@SpringBootTest
@EnableWireMock({
    @ConfigureWireMock(name = "default", filesUnderClasspath = "test-specific-mappings/test1")
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
