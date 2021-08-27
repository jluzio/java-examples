package com.example.wiremockplayground.api;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@Slf4j
class WireMockTest {

  @Autowired
  Environment environment;

  record MessageResponseBody(String message) {}

  @Test
  void test() {
    String wiremockServerPort = environment.getProperty("wiremock.server.port");

    String message = "World!";
    stubFor(get("/hello").willReturn(ok(message)));

    WebClient webClient = WebClient.builder()
        .baseUrl("http://localhost:%s/".formatted(wiremockServerPort))
        .build();

    String response = webClient.get().uri("/hello")
        .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class))
        .block();
    log.info(response);
    assertThat(response).isEqualTo(message);

    String messageSimpleResponse = webClient.get().uri("/message/simple")
        .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class))
        .block();
    log.info(messageSimpleResponse);
    assertThat(messageSimpleResponse).isEqualTo("Hello world!");

    MessageResponseBody messageBodyFileResponse = webClient.get().uri("/body-file")
        .exchangeToMono(clientResponse -> clientResponse.bodyToMono(MessageResponseBody.class))
        .block();
    log.info("{}", messageBodyFileResponse);
    assertThat(messageBodyFileResponse).isEqualTo(new MessageResponseBody("Hello!"));
  }

}
