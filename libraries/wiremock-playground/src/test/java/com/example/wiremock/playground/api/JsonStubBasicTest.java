package com.example.wiremock.playground.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@EnableWireMock({
    @ConfigureWireMock(name = "default"),
    @ConfigureWireMock(name = "customStubLocation", stubLocation = ".")
})
@Slf4j
class JsonStubBasicTest {

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

    log.debug(">>> Stub Mappings >>>");
    wiremock.getStubMappings().forEach(stubMapping ->
        log.debug("stubMapping: {}", stubMapping));
    log.debug("<<< Stub Mappings <<<");

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

    log.debug(">>> Stub Mappings >>>");
    customStubLocationWiremock.getStubMappings().forEach(stubMapping ->
        log.debug("stubMapping: {}", stubMapping));
    log.debug("<<< Stub Mappings <<<");

    assertThat(webClient.get().uri("/message/simple")
        .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class))
        .block()
    )
        .satisfies(it -> log.debug("{}", it))
        .isEqualTo("Hello world!");

    MessageResponseBody messageBodyFileResponse = webClient.get().uri("/body-file")
        .exchangeToMono(clientResponse -> clientResponse.bodyToMono(MessageResponseBody.class))
        .block();
    log.info("{}", messageBodyFileResponse);
    assertThat(messageBodyFileResponse).isEqualTo(new MessageResponseBody("Hello!"));
  }

}
