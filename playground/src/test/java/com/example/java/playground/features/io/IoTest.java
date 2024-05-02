package com.example.java.playground.features.io;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

@Slf4j
class IoTest {

  @Test
  @org.junit.jupiter.api.Disabled
  void test_write_file() throws IOException {
    var userDir = System.getProperty("user.dir");
    var file = new File(userDir, "src/test/resources/features/text.txt");

    var text = IntStream.rangeClosed(1, 20)
        .mapToObj("line %s"::formatted)
        .collect(Collectors.joining(System.lineSeparator()));
    Files.writeString(file.toPath(), text);

//        try (var writer = new BufferedWriter(new FileWriter(file))) {
//            for (var l : IntStream.rangeClosed(1, 20).toArray()) {
//                writer.write("line %s".formatted(l));
//                writer.newLine();
//            }
//            writer.flush();
//        }
  }

  @Test
  void basic_paths() {
    var basePath = Paths.get(".");
    var absolutePath = basePath.toAbsolutePath();
    var normalizedPath = basePath.normalize();
    log.info("bp: {} | ap:{} | np:{}", basePath, absolutePath, normalizedPath);

    assertThat(basePath)
        .isNotEqualTo(absolutePath)
        .isNotEqualTo(normalizedPath);
    assertThat(absolutePath)
        .isNotEqualTo(normalizedPath);

    assertThat(basePath.relativize(Path.of("src")))
        .isEqualTo(Path.of("src"));
    assertThat(basePath.relativize(Path.of("src/test/java")))
        .isEqualTo(Path.of("src/test/java"));

    assertThat(Path.of("src/test/java").getParent())
        .isEqualTo(Path.of("src/test"));
    assertThat(Path.of("src/test/java").getFileName())
        .isEqualTo(Path.of("java"));
    assertThat(Path.of("src/test/java").subpath(1, 3))
        .isEqualTo(Path.of("test/java"));
    assertThat(Path.of("src/test/java").getNameCount())
        .isEqualTo(3);
    assertThat(Path.of("src/test/java").getName(0))
        .isEqualTo(Path.of("src"));

    assertThat(Path.of("src/test/java").relativize(Path.of("src/main/java")))
        .isEqualTo(Path.of("../../main/java"));

    assertThat(Path.of("src/test/java").startsWith(Path.of("src/test")))
        .isTrue();
    assertThat(Path.of("src/test/java").endsWith(Path.of("java")))
        .isTrue();

    assertThat(Path.of("/subfolder/file").getRoot())
        .isEqualTo(Path.of("/"));
    assertThat(Path.of("subfolder/file").getRoot())
        .isNull();
  }

  @Test
  void test_read_file() throws IOException, URISyntaxException {
    var file = new File(this.getClass().getResource("/features/text.txt").toURI());
    try (var reader = new BufferedReader(new FileReader(file))) {
      var data = reader.lines()
          .map(l -> Integer.valueOf(StringUtils.substringAfter(l, " ")))
          .filter(value -> value % 2 == 0)
          .limit(5)
          .collect(Collectors.toList());
      log.info("data: {}", data);
    }

    try (Stream<String> lines = Files.lines(file.toPath())) {
      lines.forEach(v -> log.info("line: {}", v));
    }
  }

  @Test
  void test_list_files() throws IOException, URISyntaxException {
    var file = new File(this.getClass().getResource("/features").toURI());
    try (var files = Files.list(file.toPath())) {
      files.forEach(v -> log.info("file: {}", v));
    }
    try (var files = Files.list(file.toPath())) {
      files.forEach(v -> log.info("file: {}", v));
    }
  }

  @Test
  void test_find_file() throws IOException {
    var rootPath = Path.of(System.getProperty("user.dir"), "src");
    try (var pathStream = Files.find(
        rootPath,
        4,
        (path, basicFileAttributes) -> path.endsWith("text.txt"))) {
      pathStream
          .limit(3)
          .forEach(v -> log.info("matching file: {}", v));
    }
  }

  @Test
  void test_try_close() {
    var data = new StringReader("123") {
      @Override
      public void close() {
        super.close();
        log.info("close");
      }
    };
    try (data) {
//            data.read();
    }
  }

  @Test
  void test_file_mismatch() throws IOException {
    Path filePath1 = Files.createTempFile("file1", ".txt");
    Path filePath2 = Files.createTempFile("file2", ".txt");

    Files.writeString(filePath1, "JournalDev Test String");
    Files.writeString(filePath2, "JournalDev Test String");
    long mismatch = Files.mismatch(filePath1, filePath2);
    log.info("mismatch: {}", mismatch);

    Files.writeString(filePath1, "JournalDev Test String");
    Files.writeString(filePath2, "JournalDev Test Stringzz");
    long mismatch2 = Files.mismatch(filePath1, filePath2);
    log.info("mismatch: {}", mismatch2);

    filePath1.toFile().deleteOnExit();
    filePath2.toFile().deleteOnExit();

  }

}
