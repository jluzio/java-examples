package com.example.java.playground.lib.mockito;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ResultCaptor<T> implements Answer<T> {

  private T value = null;

  public T getValue() {
    return value;
  }

  @Override
  public T answer(InvocationOnMock invocationOnMock) throws Throwable {
    value = (T) invocationOnMock.callRealMethod();
    return value;
  }
}