package com.example.avro.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.SchemaParser;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.file.SeekableByteArrayInput;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

@SpringBootTest(classes = JacksonAutoConfiguration.class)
@Slf4j
class ModelTest {

  @Autowired
  ObjectMapper objectMapper;
  @Value("classpath:avro/user-previous-version.avsc")
  Resource previousSchemaResource;

  @Test
  void basic_usage() throws IOException {
    var user1 = User.newBuilder()
        .setUsername("johndoe")
        .setFirstName("John")
        .setLastName("Doe")
        .setPhoneNumber(null)
        .build();
    var user2 = User.newBuilder()
        .setUsername("janedoe")
        .setFirstName("Jane")
        .setLastName("Doe")
        .setPhoneNumber(132)
        .setAccountActive(true)
        .build();
    var user3 = User.newBuilder()
        .setUsername("mrdoe")
        .setFirstName("Mr")
        .setLastName("Doe")
        .setPhoneNumber(321)
        .setAccountActive(true)
        .build();
    var users = List.of(user1, user2, user3);
    log.debug("users: {}", users);

    // Serialize user1, user2 and user3 to memory/disk
    DatumWriter<User> userDatumWriter = new SpecificDatumWriter<>(User.class);

    ByteArrayOutputStream output = new ByteArrayOutputStream();
    DataFileWriter<User> dataFileWriter = new DataFileWriter<>(userDatumWriter);
    // create can receive a file or other OutputStreams
    dataFileWriter.create(user1.getSchema(), output);
    for (var user : users) {
      dataFileWriter.append(user);
    }
    dataFileWriter.close();

    assertThat(output.size())
        .isGreaterThan(0);
    log.debug("output: {}", output);

    // Deserialize
    DatumReader<User> userDatumReader = new SpecificDatumReader<>(User.class);
    var input = new SeekableByteArrayInput(output.toByteArray());
    DataFileReader<User> dataFileReader = new DataFileReader<>(input, userDatumReader);

    User storedUser = null;
    var storedUsers = new ArrayList<User>();
    while (dataFileReader.hasNext()) {
      // Reuse user object by passing it to next(). This saves us from
      // allocating and garbage collecting many objects for files with
      // many items.
      storedUser = dataFileReader.next(storedUser);
      log.debug("user: {}", storedUser);
      storedUsers.add(User.newBuilder(storedUser).build());
    }
    assertThat(storedUsers)
        .isEqualTo(users);
  }

  @Test
  void versioning() throws IOException {
    var schemaPreviousVersion = new SchemaParser().parse(previousSchemaResource.getFile()).mainSchema();
    var user1 = new GenericData.Record(schemaPreviousVersion);
    user1.put("username", "johndoe");
    user1.put("firstName", "John");
    user1.put("lastName", "Doe");
    var usersPreviousVersion = List.of(user1);
    log.debug("usersPreviousVersion: {}", usersPreviousVersion);

    var usersPreviousVersionMigrated = usersPreviousVersion.stream()
            .map(it -> User.newBuilder()
                .setUsername((String) it.get("username"))
                .setFirstName((String) it.get("firstName"))
                .setLastName((String) it.get("lastName"))
                .build())
                .toList();
    log.debug("usersPreviousVersionMigrated: {}", usersPreviousVersionMigrated);

    // Serialize
    DatumWriter<GenericRecord> userDatumWriter = new GenericDatumWriter<>(schemaPreviousVersion);

    ByteArrayOutputStream output = new ByteArrayOutputStream();
    DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(userDatumWriter);
    dataFileWriter.create(user1.getSchema(), output);
    for (var user : usersPreviousVersion) {
      dataFileWriter.append(user);
    }
    dataFileWriter.close();

    assertThat(output.size())
        .isGreaterThan(0);
    log.debug("output: {}", output);

    // Deserialize
    DatumReader<User> userDatumReader = new SpecificDatumReader<>(User.class);
    var input = new SeekableByteArrayInput(output.toByteArray());
    DataFileReader<User> dataFileReader = new DataFileReader<>(input, userDatumReader);

    User storedUser = null;
    var storedUsers = new ArrayList<User>();
    while (dataFileReader.hasNext()) {
      // Reuse user object by passing it to next(). This saves us from
      // allocating and garbage collecting many objects for files with
      // many items.
      storedUser = dataFileReader.next(storedUser);
      log.debug("user: {}", storedUser);
      storedUsers.add(User.newBuilder(storedUser).build());
    }
    assertThat(storedUsers)
        .isNotEqualTo(usersPreviousVersion)
        .isEqualTo(usersPreviousVersionMigrated);
  }

}
