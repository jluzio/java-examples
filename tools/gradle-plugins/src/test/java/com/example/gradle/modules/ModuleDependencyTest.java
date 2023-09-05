package com.example.gradle.modules;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.tools.moduleversioning.ModuleAService;
import com.example.tools.moduleversioning.ModuleBService;
import org.junit.jupiter.api.Test;

class ModuleDependencyTest {

  @Test
  void test() {
    var moduleAService = new ModuleAService();
    var moduleBService = new ModuleBService();

    assertThat(moduleAService.printVersion())
        .isEqualTo("ModuleA v2.0");
    assertThat(moduleBService.printVersion())
        .isEqualTo("ModuleB :: ModuleA v2.0");
  }

}
