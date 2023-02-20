package com.example.java.playground.features.io;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

@Slf4j
class DataStreamTest {


  @Test
  void test() throws IOException {
    final double[] prices = {19.99, 9.99, 15.99, 3.99, 4.99};
    final int[] units = {12, 8, 13, 29, 50};
    final String[] descs = {
        "Java T-shirt",
        "Java Mug",
        "Duke Juggling Dolls",
        "Java Pin",
        "Java Key Chain"
    };
    final int totalElements = prices.length;
    BigDecimal expectedTotalPrice = getTotalPrice(prices, units);

    ByteArrayOutputStream output = new ByteArrayOutputStream();
    DataOutputStream dataOutput = new DataOutputStream(output);

    for (int i = 0; i < totalElements; i++) {
      dataOutput.writeDouble(prices[i]);
      dataOutput.writeInt(units[i]);
      dataOutput.writeUTF(descs[i]);
    }

    DataInputStream dataInput = new DataInputStream(new ByteArrayInputStream(output.toByteArray()));
    double price;
    int unit;
    String desc;
    double totalPrice = 0.0;
    int elements = 0;

    try {
      while (true) {
        price = dataInput.readDouble();
        unit = dataInput.readInt();
        desc = dataInput.readUTF();
        log.info("You ordered {} units of {} at {}", unit, desc, price);
        totalPrice += unit * price;
        elements++;
      }
    } catch (EOFException e) {
      // done
    }

    assertThat(elements)
        .isEqualTo(totalElements);

    BigDecimal expectedScaledTotalPrice = scaledValue(expectedTotalPrice);
    log.info("expectedTotalPrice: {}", expectedScaledTotalPrice);
    assertThat(totalPrice)
        .extracting(BigDecimal::new, InstanceOfAssertFactories.BIG_DECIMAL)
        .matches(v -> Objects.equals(scaledValue(v), expectedScaledTotalPrice),
            String.format("must equal: %s", expectedScaledTotalPrice));
  }

  private BigDecimal getTotalPrice(double[] prices, int[] units) {
    Flux<Double> priceFlux = Flux.fromStream(Arrays.stream(prices).boxed());
    Flux<Integer> unitFlux = Flux.fromStream(Arrays.stream(units).boxed());
    return priceFlux
        .zipWith(unitFlux, (p, u) -> p * u)
        .collect(Collectors.summingDouble(v -> v))
        .map(BigDecimal::new)
        .block();
  }

  private BigDecimal scaledValue(BigDecimal value) {
    return value.setScale(2, RoundingMode.HALF_UP);
  }

}
