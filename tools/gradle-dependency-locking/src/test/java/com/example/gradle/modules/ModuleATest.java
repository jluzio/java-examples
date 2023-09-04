package com.example.gradle.modules;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.tools.moduleversioning.ModuleAService;
import org.junit.jupiter.api.Test;

class ModuleATest {

  @Test
  void test() {
    ModuleAService moduleAService = new ModuleAService();
    assertThat(moduleAService.printVersion())
        .isEqualTo("ModuleA v2.0");
  }

}
