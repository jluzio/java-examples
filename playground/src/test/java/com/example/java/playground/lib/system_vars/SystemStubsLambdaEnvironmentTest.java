package com.example.java.playground.lib.system_vars;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.properties.SystemProperties;

class SystemStubsLambdaEnvironmentTest {

  private static EnvironmentVariables envVars = new EnvironmentVariables()
      .set("env_var", "env_var_value");
  private static SystemProperties systemProps = new SystemProperties()
      .set("sys_props_var", "sys_props_var_value");

  @Test
  void test() throws Exception {
    envVars.execute(() -> systemProps.execute(
        () -> {
          assertThat(System.getenv("env_var"))
              .isEqualTo("env_var_value");
          assertThat(System.getProperty("sys_props_var"))
              .isEqualTo("sys_props_var_value");
        }
    ));
  }

}
