package com.example.java.playground.utils;

@FunctionalInterface
public interface LogAction {

  void log(String msg, Object... arguments);
}
