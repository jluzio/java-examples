package com.example.java.playground.lib.vavr;

import static java.util.Optional.ofNullable;

import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import java.util.Optional;
import java.util.function.Function;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ValidationTest {


  @Test
  void validation_default() {
    Function<Boolean, Validation<String, Integer>> validatorFunction =
        (Boolean computationComplete) ->
            computationComplete
                ? Validation.valid(42)
                : Validation.invalid("Invalid");
    Validation<String, Integer> validation = validatorFunction.apply(true);
    Assertions.<Validation<String, Integer>>assertThat(validation)
        .matches(Validation::isValid)
        .extracting(Validation::get)
        .isEqualTo(42);
  }

  record CombinedData(int meaningOfLife, String trimmedText) {

  }

  @Test
  void validation_combine() {
    Optional<Integer> input1 = ofNullable(42);
    String input2 = "    text    ";
    Validation<Seq<String>, CombinedData> validation = Validation.combine(
        optionalValidation(input1),
        trimmedStringValidation(input2)
    ).ap(CombinedData::new);

    Assertions.<Validation<Seq<String>, CombinedData>>assertThat(validation)
        .matches(Validation::isValid)
        .extracting(Validation::get)
        .isEqualTo(new CombinedData(42, "text"));
  }


  private Validation<String, String> trimmedStringValidation(String value) {
    return optionalValidation(
        ofNullable(value)
            .map(String::trim));
  }

  private <T> Validation<String, T> optionalValidation(Optional<T> optionalValue) {
    return optionalValue
        .map(Validation::<String, T>valid)
        .orElse(Validation.invalid("Missing value"));
  }

}
