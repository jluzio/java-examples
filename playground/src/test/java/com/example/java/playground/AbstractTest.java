package com.example.java.playground;

import com.example.java.playground.utils.LogAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public abstract class AbstractTest {
    protected Logger log = LoggerFactory.getLogger(getClass());
    protected LogAction defaultLogAction = log::info;

    protected <T> Consumer<T> valueLog(String msg) {
        return valueLog(msg, defaultLogAction);
    }

    protected <T> Consumer<T> valueLog(String msg, LogAction logAction) {
        return (T value) -> logAction.log(msg, value);
    }
}
