package com.example.gradle_playground.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.ObjectMapper;

@RestController
@Slf4j
@RequiredArgsConstructor
public class HelloController {

  private final ObjectMapper objectMapper;

  record Greeting(String name, String message){}

  @GetMapping
  public String hello(@RequestParam(defaultValue = "World") String name) {
    String message = "Hello %s".formatted(name);
    var greeting = new Greeting(name, message);
    log.info("hello :: {}", objectMapper.writeValueAsString(greeting));
    return message;
  }

}
