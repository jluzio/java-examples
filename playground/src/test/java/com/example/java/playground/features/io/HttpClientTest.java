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

@Slf4j
class HttpClientTest {
  // Module: java.net.http

  @Test
  void test_get() throws URISyntaxException, IOException, InterruptedException {
    try (HttpClient client = HttpClient.newHttpClient()) {
      // GET
      HttpRequest request = HttpRequest
          .newBuilder(new URI("https://en.wikipedia.org/wiki/Free_Willy"))
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

  @Test
  void test_get_async() throws URISyntaxException {
    try (HttpClient client = HttpClient.newHttpClient()) {
      // GET
      HttpRequest request = HttpRequest
          .newBuilder(new URI("https://en.wikipedia.org/wiki/Free_Willy"))
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
