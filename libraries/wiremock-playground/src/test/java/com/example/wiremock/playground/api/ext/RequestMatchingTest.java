package com.example.wiremock.playground.api.ext;

import static com.github.tomakehurst.wiremock.client.WireMock.absent;
import static com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
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
    @ConfigureWireMock(name = "default")
})
@Slf4j
class RequestMatchingTest {

  @Configuration
  static class Config {

  }

  @InjectWireMock("default")
  WireMockServer wiremock;

  record GreetingRequest(String target) {

  }

  @Test
  void test_matchingJsonPath() {
    WireMock.configureFor(wiremock.port());
    WebClient webClient = getWebClient();

    String apiUrl = "/hello";
    stubFor(post(apiUrl)
        .withRequestBody(matchingJsonPath("$.target"))
        .willReturn(
//            ok("Hello {{request.body.target}}!")
            ok("Hello {{jsonPath request.body '$.target'}}!")
                .withTransformers("response-template")));
    stubFor(post(apiUrl)
        .withRequestBody(matchingJsonPath("$.target", absent()))
        .willReturn(WireMock.ok("Hello world!")));

    String responseWithBody = webClient.post().uri(apiUrl)
        .bodyValue(new GreetingRequest("John"))
        .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class))
        .block();
    log.info(responseWithBody);
    assertThat(responseWithBody)
        .isEqualTo("Hello John!");

    String responseWithoutBody = webClient.post().uri(apiUrl)
        .bodyValue(new GreetingRequest(null))
        .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class))
        .block();
    log.info(responseWithoutBody);
    assertThat(responseWithoutBody)
        .isEqualTo("Hello world!");
  }

  @Test
  void test_jsonMapping_matchingJsonPath() {
    WebClient webClient = getWebClient();

    String apiUrl = "/request-matching/matching-json-path";
    String responseWithBody = webClient.post().uri(apiUrl)
        .bodyValue(new GreetingRequest("John"))
        .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class))
        .block();
    log.info(responseWithBody);
    assertThat(responseWithBody)
        .isEqualTo("Hello John!");

    String responseWithoutBody = webClient.post().uri(apiUrl)
        .bodyValue(new GreetingRequest(null))
        .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class))
        .block();
    log.info(responseWithoutBody);
    assertThat(responseWithoutBody)
        .isEqualTo("Hello world!");

    String responseWithoutBodyAlt = webClient.post().uri(apiUrl)
        .bodyValue(new GreetingRequest(null))
        .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class))
        .block();
    log.info(responseWithoutBodyAlt);
    assertThat(responseWithoutBodyAlt)
        .isEqualTo("Hello world!");
  }

  private WebClient getWebClient() {
    return WebClient.builder()
        .baseUrl(wiremock.baseUrl())
        .build();
  }

}
