package com.example.protobuf.grpc.interceptor;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogStatsServerInterceptor implements ServerInterceptor {

  private final Map<String, Integer> callCountByMethod = new ConcurrentHashMap<>();

  @Override
  public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers,
      ServerCallHandler<ReqT, RespT> next) {
    var name = call.getMethodDescriptor().getFullMethodName();
    var count = callCountByMethod.merge(name, 1, Integer::sum);
    log.info("LogStatsServerInterceptor :: call stats :: {} | {}", name, count);
    return next.startCall(call, headers);
  }
}
