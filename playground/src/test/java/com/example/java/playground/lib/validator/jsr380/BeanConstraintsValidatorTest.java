package com.example.java.playground.lib.validator.jsr380;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.ScriptAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.validation.autoconfigure.ValidationAutoConfiguration;

@SpringBootTest(classes = ValidationAutoConfiguration.class)
@Slf4j
class BeanConstraintsValidatorTest {

  @Autowired
  Validator validator;

  @Data
  @Builder
  @RequiredArgsConstructor
  @AllArgsConstructor
  @ScriptAssert(
      lang = "groovy",
      script = "_this.luggagePieces == null || _this.luggagePieces.size() <= _this.passengers.size() * 2",
      reportOn = "luggagePieces")
  static class Car {

    @NotEmpty
    private String manufacturer;
    @NotNull
    @Valid
    private List<Passenger> passengers;
    @Valid
    private List<LuggagePiece> luggagePieces;

    @NotNull
    public Passenger getDriver() {
      return passengers.stream()
          .filter(passenger -> Objects.equals(passenger.passengerType(), PassengerType.DRIVER))
          .findFirst()
          .orElse(null);
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
  void standard() {
    var passengers = new ArrayList<>(passengers(2));
    passengers.add(new Passenger(null, null));

    var car = Car.builder()
        .manufacturer("Mach 5")
        .passengers(passengers)
        .luggagePieces(null)
        .build();
    var constraintViolations = validator.validate(car);
    log.info("constraintViolations: {}", constraintViolations);
    assertThat(constraintViolations).isNotEmpty();
  }

  @Test
  void hibernate_scripting_groovy() {
    var car = Car.builder()
        .manufacturer("Mach 5")
        .passengers(passengers(4))
        .luggagePieces(luggagePieces(10))
        .build();
    var constraintViolations = validator.validate(car);
    log.info("constraintViolations: {}", constraintViolations);
    assertThat(constraintViolations).isNotEmpty();
  }

  private List<Passenger> passengers(int count) {
    return IntStream.rangeClosed(1, count)
        .mapToObj(id -> new Passenger(
            "pax-%s".formatted(id),
            id == 1 ? PassengerType.DRIVER : PassengerType.PASSENGER))
        .toList();
  }

  private List<LuggagePiece> luggagePieces(int count) {
    return IntStream.rangeClosed(1, count)
        .mapToObj(id -> new LuggagePiece("luggage_piece-%s".formatted(id)))
        .toList();
  }

}
