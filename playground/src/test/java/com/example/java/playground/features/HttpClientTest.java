package com.example.java.playground.features;

import com.example.java.playground.AbstractTest;
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
import java.nio.file.Paths;
import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class HttpClientTest extends AbstractTest {
  // Module: java.net.http

  @Test
  void test_get() throws URISyntaxException, IOException, InterruptedException {
    var pageUri = new URI("https://en.wikipedia.org/wiki/Free_Willy");

    HttpClient client = HttpClient.newHttpClient();

    // GET
    HttpResponse<String> response = client.send(
        HttpRequest
            .newBuilder(pageUri)
            .headers("Foo", "foovalue", "Bar", "barvalue")
            .GET()
            .build(),
        HttpResponse.BodyHandlers.ofString()
    );
    int statusCode = response.statusCode();
    String body = response.body();

    log.info("[get:sync] statusCode: {}{} body: {}", statusCode, System.lineSeparator(), body);
  }

  @Test
  void test_get_async()
      throws URISyntaxException, IOException, InterruptedException, ExecutionException, TimeoutException {
    var pageUri = new URI("https://en.wikipedia.org/wiki/Free_Willy");

    HttpClient client = HttpClient.newHttpClient();

    // GET
    client.sendAsync(
        HttpRequest
            .newBuilder(pageUri)
            .headers("Foo", "foovalue", "Bar", "barvalue")
            .GET()
            .build(),
        HttpResponse.BodyHandlers.ofString()
    )
        .thenApply(HttpResponse::body)
        .thenAccept(body -> log.info("[get:async] body: {}", body))
        .get()
    ;
  }

  @Test
  @Disabled
  void test_post() throws FileNotFoundException {
    HttpClient client = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_1_1)
        .followRedirects(HttpClient.Redirect.NORMAL)
        .connectTimeout(Duration.ofSeconds(20))
        .proxy(ProxySelector.of(new InetSocketAddress("proxy.example.com", 80)))
        .authenticator(Authenticator.getDefault())
        .build();

    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://foo.com/"))
        .timeout(Duration.ofMinutes(2))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofFile(Paths.get("file.json")))
        .build();

    client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        .thenApply(HttpResponse::body)
        .thenAccept(System.out::println);
  }
}
