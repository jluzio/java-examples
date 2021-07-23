package com.example.demo.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloApi {

  @Value("${hello.source:world}")
  private String source;

  @GetMapping
  public String hello() {
    return String.format("Hello %s", source);
  }

}
