package com.example.wiremockplayground.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class GreetingRouter {

  @Bean
  RouterFunction<ServerResponse> routeHelloWorld(GreetingHandler handler) {
    return RouterFunctions
        .route(
            RequestPredicates.GET("/hello")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
            handler::helloWorld);
  }

  @Bean
  RouterFunction<ServerResponse> routeHello(GreetingHandler handler) {
    return RouterFunctions
        .route(
            RequestPredicates.GET("/hello/{name}")
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
            handler::hello);
  }

}
