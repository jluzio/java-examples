package com.example.protobuf.grpc.interceptor;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.GlobalServerInterceptor;
import org.springframework.stereotype.Component;

@Component
@GlobalServerInterceptor
@Slf4j
public class LogInterceptor implements ServerInterceptor {

  @Override
  public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers,
      ServerCallHandler<ReqT, RespT> next) {
    var name = call.getMethodDescriptor().getFullMethodName();
    log.info("gRPC call :: {} | {}", name, headers);
    return next.startCall(call, headers);
  }
}
