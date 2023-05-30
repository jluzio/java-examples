package com.example.java.playground.lib.mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@Slf4j
class MockitoSettingsTest {

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

  @Mock(strictness = Mock.Strictness.LENIENT)
  TestBean mock;


  @Test
  void strictness_default() {
    mockBean(mock);

    assertThat(mock.delegate("foo")).isEqualTo("foo2");
    verify(mock).foo();
  }

  @Test
  @MockitoSettings(strictness = Strictness.LENIENT)
  void strictness_lenient() {
    var mock = mock(TestBean.class);
    mockBean(mock);

    assertThat(mock.delegate("foo")).isEqualTo("foo2");
    verify(mock).foo();
  }

  @Test
  @MockitoSettings(strictness = Strictness.WARN)
  void strictness_warn() {
    var mock = mock(TestBean.class);
    mockBean(mock);

    assertThat(mock.delegate("foo")).isEqualTo("foo2");
    verify(mock).foo();
  }

  private void mockBean(TestBean mock) {
    when(mock.delegate(anyString())).thenCallRealMethod();
    when(mock.foo()).thenReturn("foo2");
    when(mock.bar()).thenReturn("bar2");
  }

}
