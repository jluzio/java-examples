package com.example.http_test;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class SimpleHttpClientTest {

  // see readme for logging
  @Test
  void retrieve() throws IOException, InterruptedException {
    var client = new SimpleHttpClient();
    client.retrieve("https://jsonplaceholder.typicode.com/todos/1");
  }
}