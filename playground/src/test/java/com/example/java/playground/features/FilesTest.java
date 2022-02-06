package com.example.java.playground.features;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

@Slf4j
class FilesTest {

  final DefaultResourceLoader loader = new DefaultResourceLoader();


  @Test
  void pathMatcher() throws IOException {
    Resource ymlResource = loader.getResource("classpath:application.yml");
    assertThat(ymlResource).isNotNull();
    Path ymlPath = ymlResource.getFile().toPath();

    Resource txtResource = loader.getResource("classpath:features/text.txt");
    assertThat(txtResource).isNotNull();
    Path txtPath = txtResource.getFile().toPath();

    PathMatcher fullPathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.{yml,txt}");
    PathMatcher namePathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.{yml,txt}");

    assertThat(fullPathMatcher.matches(txtPath)).isTrue();
    assertThat(fullPathMatcher.matches(ymlPath)).isTrue();
    assertThat(namePathMatcher.matches(txtPath.getFileName())).isTrue();
    assertThat(namePathMatcher.matches(ymlPath.getFileName())).isTrue();
  }

  @Test
  void directoryStream() throws IOException {
    try (DirectoryStream<Path> paths = Files.newDirectoryStream(Path.of("."))) {
      List<String> files = StreamSupport.stream(paths.spliterator(), false)
          .map(Path::getFileName)
          .map(Object::toString)
          .collect(Collectors.toList());
      log.debug("files: {}", files);
      assertThat(files).isNotEmpty();
    }
  }

  @Test
  void fileVisitor() throws IOException {
    FileVisitor<Path> fileVisitor = new SimpleFileVisitor<>() {
      final PathMatcher ignoredDirectoryMatcher = FileSystems.getDefault().getPathMatcher("glob:.*");

      @Override
      public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
          throws IOException {
        log.debug("preVisitDirectory: {}", dir);
        Path dirName = dir.toRealPath().getFileName();
        if (ignoredDirectoryMatcher.matches(dirName)) {
          return FileVisitResult.SKIP_SUBTREE;
        } else {
          return super.preVisitDirectory(dir, attrs);
        }
      }

      @Override
      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        log.debug("visitFile: {}", file);
        return super.visitFile(file, attrs);
      }
    };

    Files.walkFileTree(
        Path.of("."),
        fileVisitor);
  }

}
