package com.example.java.playground.features;

import com.example.java.playground.AbstractTest;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IoTest extends AbstractTest {
    @Test
    @org.junit.jupiter.api.Disabled
    void test_write_file() throws IOException {
        var userDir = System.getProperty("user.dir");
        var file = new File(userDir, "src/test/resources/features/text.txt");
        try (var writer = new BufferedWriter(new FileWriter(file))) {
            for (var l : IntStream.rangeClosed(1, 20).toArray()) {
                writer.write("line %s".formatted(l));
                writer.newLine();
            }
            writer.flush();
        }
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

        Files.lines(file.toPath()).forEach(valueLog("line: {}"));
    }

    @Test
    void test_list_files() throws IOException, URISyntaxException {
        var file = new File(this.getClass().getResource("/features").toURI());
        Files.list(file.toPath()).forEach(valueLog("file: {}"));
    }

    @Test
    void test_find_file() throws IOException {
        var rootPath = Path.of(System.getProperty("user.dir"), "src");
        Files.find(rootPath,
                4,
                (path, basicFileAttributes) -> path.endsWith("text.txt"))
        .limit(3)
        .forEach(valueLog("matching file: {}"));

    }
}
