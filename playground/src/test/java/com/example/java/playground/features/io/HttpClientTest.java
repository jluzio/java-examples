package com.example.java.playground.features.io;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import uk.org.webcompere.systemstubs.properties.SystemProperties;

// https://docs.oracle.com/en/java/javase/21/docs/api/java.net.http/module-summary.html
@SpringBootTest(
    properties = {
//        "logging.level.jdk.httpclient.HttpClient=INFO",
        "logging.level.jdk.httpclient.HttpClient=DEBUG",
//        "logging.level.jdk.httpclient.HttpClient=ERROR",
    }
)
@ExtendWith(SystemStubsExtension.class)
@Slf4j
class HttpClientTest {
  // Module: java.net.http

  // System Properties for logging -Djdk.httpclient.HttpClient.log=<value>
  @SystemStub
  private static SystemProperties systemProps = new SystemProperties()
      .set("jdk.httpclient.HttpClient.log", "requests,headers")
//      .set("jdk.httpclient.HttpClient.log", "requests,headers,trace")
//      .set("jdk.httpclient.HttpClient.log", "errors,requests,headers")
//      .set("jdk.httpclient.HttpClient.log", "errors,requests,headers,frames:control:data:window,ssl,trace,channel")
//      .set("jdk.httpclient.HttpClient.log", "errors,requests,headers,frames:all,ssl,trace,channel")
      ;

  @Configuration
  static class Config {

  }

  @Test
  void test_get() throws URISyntaxException, IOException, InterruptedException {
    var client = defaultHttpClient();
    try (client) {
      // GET
      HttpRequest request = HttpRequest
          .newBuilder(new URI("https://jsonplaceholder.typicode.com/todos/1"))
          .headers("Foo", "foovalue", "Bar", "barvalue")
          .GET()
          .build();
      HttpResponse<String> response = client.send(
          request, BodyHandlers.ofString());
      int statusCode = response.statusCode();
      String body = response.body();

      log.info("[get:sync] statusCode: {}{} body: {}", statusCode, System.lineSeparator(), body);
    }
  }

  private HttpClient defaultHttpClient() {
    return HttpClient.newBuilder()
        .followRedirects(Redirect.NORMAL)
        .connectTimeout(Duration.ofSeconds(30))
        .build();
  }

  @Test
  void test_get_async() throws URISyntaxException {
    var client = defaultHttpClient();
    try (client) {
      // GET
      HttpRequest request = HttpRequest
          .newBuilder(new URI("https://jsonplaceholder.typicode.com/todos/1"))
          .headers("Foo", "foovalue", "Bar", "barvalue")
          .GET()
          .build();
      CompletableFuture<String> responseFuture = client.sendAsync(
              request, BodyHandlers.ofString())
          .thenApply(HttpResponse::body);

      assertThat(responseFuture)
          .isNotDone();

      await()
          .until(responseFuture::isDone);

      assertThat(responseFuture)
          .isDone()
          .isNotCompletedExceptionally();
      // or responseFuture.get() if we wanted to wait
      log.info("[get:async] body: {}", responseFuture.resultNow());
    }
  }

  @Test
  @Disabled
  void test_post() throws FileNotFoundException, ExecutionException, InterruptedException {
    try (HttpClient client = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_1_1)
        .followRedirects(HttpClient.Redirect.NORMAL)
        .connectTimeout(Duration.ofSeconds(20))
        .proxy(ProxySelector.of(new InetSocketAddress("proxy.example.com", 80)))
        .authenticator(Authenticator.getDefault())
        .build()
    ) {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("https://foo.com/"))
          .timeout(Duration.ofMinutes(2))
          .header("Content-Type", "application/json")
          .POST(HttpRequest.BodyPublishers.ofFile(Paths.get("file.json")))
          .build();

      client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
          .thenApply(HttpResponse::body)
          .thenAccept(System.out::println)
          .get();
    }
  }
}
