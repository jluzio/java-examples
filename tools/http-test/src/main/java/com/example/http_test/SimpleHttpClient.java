package com.example.http_test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleHttpClient {

  public HttpResponse<String> retrieve(String uri) throws IOException, InterruptedException {
    log.info("retrieving: {}", uri);
    var client = HttpClient.newBuilder()
        .followRedirects(Redirect.NORMAL)
        .build();
    var request = HttpRequest.newBuilder(URI.create(uri))
        .GET()
        .build();
    HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
    log.info("response: {}", response);
    log.info("response.body: {}", response.body());
    return response;
  }

}
