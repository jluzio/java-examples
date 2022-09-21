package com.example.java.playground.lib.mockito;

import java.util.ArrayList;
import java.util.List;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ResultCaptor<T> implements Answer<T> {

  private final List<T> allValues = new ArrayList<>();

  public T getValue() {
    return allValues.stream()
        .reduce((first, second) -> second)
        .orElse(null);
  }

  public List<T> getAllValues() {
    return allValues;
  }

  @Override
  public T answer(InvocationOnMock invocationOnMock) throws Throwable {
    var value = (T) invocationOnMock.callRealMethod();
    allValues.add(value);
    return value;
  }
}