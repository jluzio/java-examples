package com.example.java.playground.features;

import com.example.java.playground.AbstractTest;
import org.junit.jupiter.api.Test;

public class InterfaceTest extends AbstractTest {

    interface SampleInterface {
        default int defaultFoo() {
            return 42;
        }
        static int staticFoo() {
            return 42;
        }
    }

    @Test
    void test() {
        var instance = new SampleInterface() {};
        log.info("{}", SampleInterface.staticFoo());
        log.info("{}", instance.defaultFoo());

        var instance2 = new SampleInterface() {
            @Override
            public int defaultFoo() {
                return 43;
            }
        };
        log.info("{}", instance2.defaultFoo());

    }
}
