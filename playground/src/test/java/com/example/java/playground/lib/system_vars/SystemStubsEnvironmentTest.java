package com.example.java.playground.lib.system_vars;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import uk.org.webcompere.systemstubs.properties.SystemProperties;

@ExtendWith(SystemStubsExtension.class)
class SystemStubsEnvironmentTest {

  @SystemStub
  private static EnvironmentVariables envVars = new EnvironmentVariables()
      .set("env_var", "env_var_value");
  @SystemStub
  private static SystemProperties systemProps = new SystemProperties()
      .set("sys_props_var", "sys_props_var_value");

  @Test
  void test() {
    assertThat(System.getenv("env_var"))
        .isEqualTo("env_var_value");
    assertThat(System.getProperty("sys_props_var"))
        .isEqualTo("sys_props_var_value");
  }

}
