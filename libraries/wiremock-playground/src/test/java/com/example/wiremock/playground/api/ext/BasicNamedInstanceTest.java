package com.example.wiremock.playground.api.ext;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;
import org.wiremock.spring.InjectWireMock;

@SpringBootTest
@EnableWireMock({
    @ConfigureWireMock(name = "default")
})
@Slf4j
class BasicNamedInstanceTest {

  @Configuration
  static class Config {

  }

  @Autowired
  Environment environment;
  @InjectWireMock("default")
  WireMockServer wiremock;

  record MessageResponseBody(String message) {

  }

  @Test
  void test() {
    String wiremockServerUrl = requireNonNull(environment.getProperty("wiremock.server.baseUrl"));
    assertThat(wiremock.baseUrl())
        .isEqualTo(wiremockServerUrl);
    WebClient webClient = WebClient.builder()
        .baseUrl(wiremock.baseUrl())
        .build();

    String message = "World!";
    wiremock.stubFor(get("/hello")
        .willReturn(ok(message)));

    WireMock.configureFor(wiremock.port());
    stubFor(get("/hello2")
        .willReturn(ok(message)));

    String response = webClient.get().uri("/hello")
        .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class))
        .block();
    log.info(response);
    assertThat(response).isEqualTo(message);

    String response2 = webClient.get().uri("/hello2")
        .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class))
        .block();
    log.info(response2);
    assertThat(response2).isEqualTo(message);
  }

}
