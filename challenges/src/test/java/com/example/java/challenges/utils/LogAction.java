package com.example.java.challenges.utils;

@FunctionalInterface
public interface LogAction {
    void log(String msg, Object... arguments);
}
