package com.example.java.playground.lib.mapper.modelmapper;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

class BasicsTest {

  @Test
  void default_mappings() {
    var order = new Order(
        new Customer(new Name("firstName", "lastName")),
        new Address("street", "city")
    );

    ModelMapper modelMapper = new ModelMapper();
    OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
    var expectedOrderDTO = new OrderDTO(
        "firstName", "lastName", "street", "city");
    assertThat(orderDTO)
        .isEqualTo(expectedOrderDTO);
  }

  @Test
  void custom_mappings() {
    var order = new MismatchOrder(
        new Customer(new Name("firstName", "lastName")),
        new Address("street", "city")
    );

    ModelMapper modelMapper = new ModelMapper();

    modelMapper.typeMap(MismatchOrder.class, OrderDTO.class).addMappings(mapper -> {
      mapper.map(src -> src.getOtherAddress().getStreet(),
          OrderDTO::setBillingStreet);
      mapper.map(src -> src.getOtherAddress().getCity(),
          OrderDTO::setBillingCity);
    });

    OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
    var expectedOrderDTO = new OrderDTO(
        "firstName", "lastName", "street", "city");
    assertThat(orderDTO)
        .isEqualTo(expectedOrderDTO);
  }

  @Data
  @Builder
  @RequiredArgsConstructor
  @AllArgsConstructor
  static class Order {

    private Customer customer;
    private Address billingAddress;
  }

  @Data
  @Builder
  @RequiredArgsConstructor
  @AllArgsConstructor
  static class Customer {

    private Name name;
  }

  @Data
  @Builder
  @RequiredArgsConstructor
  @AllArgsConstructor
  static class Name {

    private String firstName;
    private String lastName;
  }

  @Data
  @Builder
  @RequiredArgsConstructor
  @AllArgsConstructor
  static class Address {

    private String street;
    private String city;
  }

  @Data
  @Builder
  @RequiredArgsConstructor
  @AllArgsConstructor
  static class OrderDTO {

    private String customerFirstName;
    private String customerLastName;
    private String billingStreet;
    private String billingCity;
  }

  @Data
  @Builder
  @RequiredArgsConstructor
  @AllArgsConstructor
  static class MismatchOrder {

    private Customer customer;
    private Address otherAddress;
  }

}
