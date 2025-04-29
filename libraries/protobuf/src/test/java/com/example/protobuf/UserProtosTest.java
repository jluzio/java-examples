package com.example.protobuf;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.proto.UserProtos;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class UserProtosTest {

  @Test
  void write_and_read() throws IOException {
    var user = UserProtos.User.newBuilder()
        .setId(1)
        .setName("John Doe")
        .setUsername("john.doe@server.org")
        .setAddress(UserProtos.Address.newBuilder()
            .setCountry("PT")
            .setCity("City")
            .setStreet("Street")
            .build())
        .build();
    log.info("user: {}", user);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    user.writeTo(outputStream);
    log.info("user output: {}", outputStream);

    var parsedUser = UserProtos.User.parseFrom(outputStream.toByteArray());
    log.info("parsedUser: {}", parsedUser);

    assertThat(user)
        .isEqualTo(parsedUser);
  }

}