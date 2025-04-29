package com.example.protobuf.config;

import com.example.proto.hello.SimpleGrpc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
public class ClientConfig {

  @Bean
  SimpleGrpc.SimpleBlockingStub stub(GrpcChannelFactory channels) {
    return SimpleGrpc.newBlockingStub(channels.createChannel("local"));
  }

/*
  @Bean
  SimpleGrpc.SimpleBlockingStub stub(GrpcChannelFactory channels) {
    return SimpleGrpc.newBlockingStub(channels.createChannel("0.0.0.0:9090"));
  }
*/

}
