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
        Path.of("target/test-classes/web-server-data").toRealPath(),
        OutputLevel.INFO
    );
    var featuresDirHandler = SimpleFileServer.createFileHandler(
        Path.of("target/test-classes/features").toRealPath());
    server.createContext("/features", featuresDirHandler);

    server.start();
    try {
      RestTemplate restTemplate = new RestTemplate();
      String helloData = restTemplate.getForObject("http://localhost:8000/hello.txt", String.class);
      assertThat(helloData)
          .isEqualTo("Hello world!");
      String textData = restTemplate.getForObject("http://localhost:8000/features/text.txt", String.class);
      assertThat(textData)
          .isNotEmpty();
    } finally {
      server.stop(0);
    }
  }

}
