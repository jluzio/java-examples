package com.example.java.playground.lib.validator.jsr380;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Validator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ValidationAutoConfiguration.class)
@Slf4j
class MethodConstraintsValidatorTest {

  @Autowired
  Validator validator;

  @Data
  static class Car {

    private final @NotEmpty String manufacturer;
    private final @Getter List<Passenger> passengers = new ArrayList<>();
    private List<LuggagePiece> luggagePieces;

    @NotNull
    public Passenger getDriver() {
      return passengers.stream()
          .filter(passenger -> Objects.equals(passenger.passengerType(), PassengerType.DRIVER))
          .findFirst()
          .orElse(null);
    }

    public void drive(@Max(500) int speed) {
      log.debug("Driving at {}", speed);
    }

    @ParameterScriptAssert(lang = "groovy", script = "luggagePieces.size() <= passengers.size() * 2")
    public void load(List<Person> passengers, List<LuggagePiece> luggagePieces) {
      log.debug("Loading in car: passengers={} | luggage={}", passengers, luggagePieces);
    }
  }

  record Passenger(@NotEmpty String name, @NotNull PassengerType passengerType) {

  }

  record LuggagePiece(String name) {

  }

  enum PassengerType {
    DRIVER, PASSENGER;
  }

  @Test
  void method_params() throws Exception {
    var car = new Car("Mach 5");
    var method = Car.class.getMethod("drive", int.class);
    var constraintViolations = validator.forExecutables()
        .validateParameters(car, method, new Object[]{999});
    log.debug("constraintViolations: {}", constraintViolations);
    assertThat(constraintViolations).isNotEmpty();
  }

  @Test
  void method_params_hibernate_scripting_groovy() throws Exception {
    var car = new Car("Mach 5");
    var passengers = IntStream.rangeClosed(1, 4)
        .mapToObj(id -> new Passenger("pax-%s".formatted(id), PassengerType.PASSENGER))
        .toList();
    var luggagePieces = IntStream.rangeClosed(1, 10)
        .mapToObj(id -> new LuggagePiece("luggage_piece-%s".formatted(id)))
        .toList();
    var method = Car.class.getMethod("load", List.class, List.class);
    var constraintViolations = validator.forExecutables()
        .validateParameters(car, method, new Object[]{
            passengers,
            luggagePieces
        });
    log.debug("constraintViolations: {}", constraintViolations);
    assertThat(constraintViolations).isNotEmpty();
  }

  @Test
  void method_result() throws Exception {
    var car = new Car("Mach 5");
    var method = Car.class.getMethod("getDriver");
    var constraintViolations = validator.forExecutables()
        .validateReturnValue(car, method, car.getDriver());
    log.debug("constraintViolations: {}", constraintViolations);
    assertThat(constraintViolations).isNotEmpty();
  }

}
