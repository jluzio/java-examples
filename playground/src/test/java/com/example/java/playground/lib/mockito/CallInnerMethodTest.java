package com.example.java.playground.lib.mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@Slf4j
class CallInnerMethodTest {

  static class TestBean {

    public String delegate(String value) {
      return Objects.equals(value, "foo") ? foo() : bar();
    }

    public String foo() {
      return "foo";
    }

    public String bar() {
      return "bar";
    }
  }


  @Test
  void using_mock() {
    var mock = mock(TestBean.class);
    when(mock.delegate(anyString())).thenCallRealMethod();

    assertThat(mock.delegate("foo")).isNull();
    verify(mock).foo();

    clearInvocations(mock);;
    assertThat(mock.delegate("bar")).isNull();
    verify(mock).bar();
  }

}
