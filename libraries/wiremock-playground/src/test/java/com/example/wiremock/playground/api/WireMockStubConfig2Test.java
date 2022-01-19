package com.example.wiremock.playground.api;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest
@AutoConfigureWireMock(port = 0, stubs = "classpath:/test-specific-mappings/test2")
@Slf4j
class WireMockStubConfig2Test {

  @Autowired
  Environment environment;

  @Test
  void test() {
    String wiremockServerPort = environment.getProperty("wiremock.server.port");

    WebClient webClient = WebClient.builder()
        .baseUrl("http://localhost:%s/".formatted(wiremockServerPort))
        .build();

    String messageSimpleResponse = webClient.get().uri("/message/custom")
        .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class))
        .block();
    log.info(messageSimpleResponse);
    assertThat(messageSimpleResponse).isEqualTo("Hello world - Test2!");
  }

}
