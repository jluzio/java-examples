package com.example.gradle_playground;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class ExampleIT {

  @Test
  void validate_jar_present() throws IOException {
    var outputLibsDir = Path.of("build/libs");
    var outputJars = Files.list(outputLibsDir).toList();
    assertThat(outputJars)
        .isNotEmpty();
  }
}
