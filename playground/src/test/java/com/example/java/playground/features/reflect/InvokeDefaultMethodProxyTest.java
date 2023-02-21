package com.example.java.playground.features.reflect;

import static java.lang.ClassLoader.getSystemClassLoader;
import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;

class InvokeDefaultMethodProxyTest {

  interface HelloWorld {

    default String hello() {
      return "world";
    }
  }

  @Test
  void test() throws Exception {
    Object proxy = Proxy.newProxyInstance(
        getSystemClassLoader(),
        new Class<?>[]{HelloWorld.class},
        (prox, method, args) -> {
          if (method.isDefault()) {
            return InvocationHandler.invokeDefault(prox, method, args);
          }
          throw new UnsupportedOperationException();
        }
    );
    Method method = proxy.getClass().getMethod("hello");
    Object result = method.invoke(proxy);
    assertThat(result)
        .isEqualTo("world");
  }
}
