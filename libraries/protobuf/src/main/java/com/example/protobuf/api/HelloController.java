package com.example.protobuf.api;


import com.example.proto.hello.HelloReply;
import com.example.proto.hello.HelloRequest;
import com.example.proto.hello.SimpleGrpc;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class HelloController {

  private final SimpleGrpc.SimpleBlockingStub helloClient;

  @GetMapping("/hello")
  public String hello(@RequestParam String name) {
    HelloReply helloReply = helloClient.sayHello(HelloRequest.newBuilder()
        .setName(name)
        .build());
    return helloReply.getMessage();
  }

}
