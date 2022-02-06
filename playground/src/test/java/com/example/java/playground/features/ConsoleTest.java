package com.example.java.playground.features;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Console;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class ConsoleTest {

  @Test
  void test() {
    // doesn't work if not in a interactive terminal
    Console console = System.console();
    assertThat(console).isNotNull();

    console.writer().println("Username: ");
    String username = console.readLine();
    console.writer().println("Password: ");
    char[] password = console.readPassword();

    assertThat(username).isNotEmpty();
    assertThat(password).isNotEmpty();
  }

}
