package com.example.java.playground.features.io;

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
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
class FilesTest {

  final DefaultResourceLoader loader = new DefaultResourceLoader();


  @Test
  void pathMatcher() throws IOException {
    Path ymlPath = getYmlPath();
    Path txtPath = getTxtPath();

    PathMatcher fullPathMatcher = FileSystems.getDefault().getPathMatcher("glob:**/*.{yml,txt}");
    PathMatcher namePathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.{yml,txt}");

    assertThat(fullPathMatcher.matches(txtPath)).isTrue();
    assertThat(fullPathMatcher.matches(ymlPath)).isTrue();
    assertThat(namePathMatcher.matches(txtPath.getFileName())).isTrue();
    assertThat(namePathMatcher.matches(ymlPath.getFileName())).isTrue();
  }

  @Test
  void directoryStream() throws IOException {
    try (DirectoryStream<Path> paths = Files.newDirectoryStream(currentPath())) {
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
      final PathMatcher ignoredDirectoryMatcher = FileSystems.getDefault()
          .getPathMatcher("glob:.*");

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
        currentPath(),
        fileVisitor);
  }

  @Test
  void watchService() throws IOException, InterruptedException {
    WatchService watcher = FileSystems.getDefault().newWatchService();

    var dir = targetClassesPath();
    dir.register(watcher,
        StandardWatchEventKinds.ENTRY_CREATE,
        StandardWatchEventKinds.ENTRY_MODIFY,
        StandardWatchEventKinds.ENTRY_DELETE);

    Path newFile = dir.resolve("file.tmp");
    if (Files.exists(newFile)) {
      Files.delete(newFile);
    }

    Flux.interval(Duration.ofMillis(100))
        .take(2)
        .publishOn(Schedulers.parallel())
        .doOnNext(ignored -> {
          try {
            if (!Files.exists(newFile)) {
              log.debug("Creating file: {}", newFile);
              Files.createFile(newFile);
            } else {
              log.debug("Deleting file: {}", newFile);
              Files.delete(newFile);
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        })
        .blockLast();

    log.debug("polling events");
    WatchKey watchKey = watcher.poll(1, TimeUnit.SECONDS);
    log.debug("watchKey: {}", watchKey);
    if (watchKey != null) {
      watchKey.pollEvents()
          .forEach(e -> log.debug("event: {}#{} | ctx: {}", e.kind(), e.count(), e.context()));
    }
  }

  @Test
  void probeContents() throws IOException {
    assertThat(Files.probeContentType(getTxtPath()))
        .isEqualTo("text/plain");
  }

  private Path currentPath() {
    return Path.of(".");
  }

  private Path targetClassesPath() throws IOException {
    return Path.of("target/classes");
//    return loader.getResource("classpath:application.yml").getFile().toPath().getParent();
  }

  private Path getTxtPath() throws IOException {
    Resource txtResource = loader.getResource("classpath:features/text.txt");
    assertThat(txtResource).isNotNull();
    Path txtPath = txtResource.getFile().toPath();
    return txtPath;
  }

  private Path getYmlPath() throws IOException {
    Resource ymlResource = loader.getResource("classpath:application.yml");
    assertThat(ymlResource).isNotNull();
    Path ymlPath = ymlResource.getFile().toPath();
    return ymlPath;
  }
}
