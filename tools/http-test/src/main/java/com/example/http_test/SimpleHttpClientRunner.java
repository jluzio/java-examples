package com.example.http_test;

import java.io.IOException;

public class SimpleHttpClientRunner {

  public static void main(String[] args) throws IOException, InterruptedException {
    var client = new SimpleHttpClient();
    client.retrieve(args[0]);
  }

}
