package com.example.wiremock.playground.api;

import static com.github.tomakehurst.wiremock.client.WireMock.absent;
import static com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.client.WireMock;
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
class RequestMatchingTest {

  @Autowired
  Environment environment;

  record GreetingRequest(String target) {

  }

  @Test
  void test_matchingJsonPath() {
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

    String apiUrlAlt = "/request-matching/matching-json-path-alt";
    String responseWithoutBodyAlt = webClient.post().uri(apiUrl)
        .bodyValue(new GreetingRequest(null))
        .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class))
        .block();
    log.info(responseWithoutBodyAlt);
    assertThat(responseWithoutBodyAlt)
        .isEqualTo("Hello world!");
  }

  private WebClient getWebClient() {
    String wiremockServerPort = environment.getProperty("wiremock.server.port");
    WebClient webClient = WebClient.builder()
        .baseUrl("http://localhost:%s/".formatted(wiremockServerPort))
        .build();
    return webClient;
  }

}
