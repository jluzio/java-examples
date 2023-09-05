package com.example.tools.moduleversioning;

public class ModuleBService {

  public String printVersion() {
    ModuleAService moduleAService = new ModuleAService();
    return "ModuleB :: %s".formatted(moduleAService.printVersion());
  }

}
