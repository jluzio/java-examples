package com.example.java.playground.features.webserver;

import static org.assertj.core.api.Assertions.assertThat;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.SimpleFileServer;
import com.sun.net.httpserver.SimpleFileServer.OutputLevel;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

class WebServerTest {

  @Test
  void test() throws IOException {
    HttpServer server = SimpleFileServer.createFileServer(
        new InetSocketAddress("localhost", 8000),
        Path.of("target/test-classes/features").toRealPath(),
        OutputLevel.INFO
    );

    server.start();
    try {
      RestTemplate restTemplate = new RestTemplate();
      String data = restTemplate.getForObject("http://localhost:8000/text.txt", String.class);
      assertThat(data)
          .isNotNull();
    } finally {
      server.stop(0);
    }
  }

}
