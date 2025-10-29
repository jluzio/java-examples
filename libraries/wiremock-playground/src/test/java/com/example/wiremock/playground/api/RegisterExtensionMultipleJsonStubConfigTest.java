package com.example.wiremock.playground.api;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest
@Slf4j
class RegisterExtensionMultipleJsonStubConfigTest {

  @RegisterExtension
  static WireMockExtension wm1 = WireMockExtension.newInstance()
      .options(wireMockConfig()
          .dynamicPort()
          .dynamicHttpsPort())
      .build();

  @RegisterExtension
  static WireMockExtension wm2 = WireMockExtension.newInstance()
      .options(wireMockConfig()
          .dynamicPort()
          .dynamicHttpsPort()
          .usingFilesUnderClasspath("custom-stubs")
      )
      .build();


  @Configuration
  static class Config {

  }

  record MessageResponseBody(String message) {

  }

  @Test
  void test_config1() {
    WireMockRuntimeInfo wmRuntimeInfo = wm1.getRuntimeInfo();
    WebClient webClient = WebClient.builder()
        .baseUrl(wmRuntimeInfo.getHttpBaseUrl())
        .build();

    logStubMappings(wmRuntimeInfo);

    assertThat(webClient.get().uri("/default/hello")
        .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class))
        .block()
    )
        .satisfies(it -> log.debug("{}", it))
        .isEqualTo("Hello world!");
  }

  @Test
  void test_config2() {
    WireMockRuntimeInfo wmRuntimeInfo = wm2.getRuntimeInfo();
    WebClient webClient = WebClient.builder()
        .baseUrl(wmRuntimeInfo.getHttpBaseUrl())
        .build();

    logStubMappings(wmRuntimeInfo);

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

  private void logStubMappings(WireMockRuntimeInfo wmRuntimeInfo) {
    log.debug(">>> Stub Mappings >>>");
    wmRuntimeInfo.getWireMock().allStubMappings().getMappings().forEach(stubMapping ->
        log.debug("stubMapping: {}", stubMapping));
    log.debug("<<< Stub Mappings <<<");
  }

}
