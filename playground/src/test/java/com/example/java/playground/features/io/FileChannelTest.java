package com.example.java.playground.features.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

class FileChannelTest {

  public static final int MAX_BUFFER_SIZE = 1024;
  private final ResourceLoader resourceLoader = new DefaultResourceLoader();

  @Test
  void givenFile_whenReadWithFileChannelUsingRandomAccessFile_thenCorrect() throws IOException {
    File file = resourceLoader.getResource("classpath:test_read.txt").getFile();
    try (var reader = new RandomAccessFile(file, "r"); var channel = reader.getChannel()) {
      int bufferSize = Math.min(MAX_BUFFER_SIZE, (int) channel.size());
      ByteBuffer buff = ByteBuffer.allocate(bufferSize);

      String fileContent = fullyReadToString(channel, buff);
      assertEquals("Hello world", fileContent);

      String reReadFileContent = fullyReadToString(channel.position(0), buff);
      assertEquals(fileContent, reReadFileContent);
    }
  }

  private String fullyReadToString(FileChannel channel, ByteBuffer buff) throws IOException {
    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      while (channel.read(buff) > 0) {
        out.write(buff.array(), 0, buff.position());
        buff.clear();
      }
      return out.toString(StandardCharsets.UTF_8);
    }
  }

}
