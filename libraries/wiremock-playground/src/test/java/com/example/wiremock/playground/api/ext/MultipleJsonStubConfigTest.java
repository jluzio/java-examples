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
    @ConfigureWireMock(name = "default"),
    @ConfigureWireMock(name = "customStubLocation", filesUnderClasspath = "custom-stubs")
})
@Slf4j
class MultipleJsonStubConfigTest {

  @Configuration
  static class Config {

  }

  @InjectWireMock("default")
  WireMockServer wiremock;
  @InjectWireMock("customStubLocation")
  WireMockServer customStubLocationWiremock;

  record MessageResponseBody(String message) {

  }

  @Test
  void test_default() {
    WebClient webClient = WebClient.builder()
        .baseUrl(wiremock.baseUrl())
        .build();

    logStubMappings(wiremock);

    assertThat(webClient.get().uri("/default/hello")
        .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class))
        .block()
    )
        .satisfies(it -> log.debug("{}", it))
        .isEqualTo("Hello world!");
  }

  @Test
  void test_customStubLocation() {
    WebClient webClient = WebClient.builder()
        .baseUrl(customStubLocationWiremock.baseUrl())
        .build();

    logStubMappings(customStubLocationWiremock);

    assertThat(webClient.get().uri("/custom/message/simple")
        .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class))
        .block()
    )
        .satisfies(it -> log.debug("{}", it))
        .isEqualTo("Hello world!");

    MessageResponseBody messageBodyFileResponse = webClient.get().uri("/custom/body-file")
        .exchangeToMono(clientResponse -> clientResponse.bodyToMono(MessageResponseBody.class))
        .block();
    log.info("{}", messageBodyFileResponse);
    assertThat(messageBodyFileResponse).isEqualTo(new MessageResponseBody("Hello!"));
  }

  private void logStubMappings(WireMockServer wiremock) {
    log.debug(">>> Stub Mappings >>>");
    wiremock.getStubMappings().forEach(stubMapping ->
        log.debug("stubMapping: {}", stubMapping));
    log.debug("<<< Stub Mappings <<<");
  }
}
