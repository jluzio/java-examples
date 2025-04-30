package com.example.protobuf.grpc;

import com.example.proto.hello.FancyGreeterGrpc;
import com.example.proto.hello.HelloReply;
import com.example.proto.hello.HelloRequest;
import com.example.protobuf.grpc.interceptor.LogStatsInterceptor;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService(interceptors = LogStatsInterceptor.class)
@Slf4j
class FancyGreeterGrpcServerService extends FancyGreeterGrpc.FancyGreeterImplBase {

  @Override
  public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
    log.info("Hello {}", req.getName());
    if (req.getName().startsWith("error")) {
      throw new IllegalArgumentException("Bad name: " + req.getName());
    }
    if (req.getName().startsWith("internal")) {
      throw new RuntimeException();
    }
    HelloReply reply = HelloReply.newBuilder().setMessage("Hello ==> " + req.getName()).build();
    responseObserver.onNext(reply);
    responseObserver.onCompleted();
  }

}